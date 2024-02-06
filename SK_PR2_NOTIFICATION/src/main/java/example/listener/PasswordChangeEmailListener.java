package example.listener;

import example.dto.KorisniciDto;
import example.dto.NotifikacijeCreateDTO;
import example.dto.PasswordChangedDto;
import example.dto.TipNotifikacijeDTO;
import example.helper.MailTekstFormater;
import example.helper.MessageHelper;
import example.service.NotifikacijaService;
import example.service.TipNotifikacijeService;
import example.service.impl.EmailServiceImpl;
import javassist.NotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import java.sql.Date;
import java.time.LocalDate;

@Component
public class PasswordChangeEmailListener {
    private MessageHelper messageHelper;
    private NotifikacijaService notifikacijaService;
    private TipNotifikacijeService tipNotifikacijeService;
    private EmailServiceImpl emailService;

    private RestTemplate userServiceApiClient;

    public PasswordChangeEmailListener(MessageHelper messageHelper, NotifikacijaService notifikacijaService, TipNotifikacijeService tipNotifikacijeService, EmailServiceImpl emailService, RestTemplate userServiceApiClient) {
        this.messageHelper = messageHelper;
        this.notifikacijaService = notifikacijaService;
        this.tipNotifikacijeService = tipNotifikacijeService;
        this.emailService = emailService;
        this.userServiceApiClient = userServiceApiClient;
    }

    //passwordChanged=password_changed
    @JmsListener(destination = "${destination.passwordChanged}", concurrency = "5-10")
    public void sendPasswordNotif(Message message) throws JMSException, IllegalAccessException, NotFoundException {
        PasswordChangedDto passwordChangedDto = messageHelper.getMessage(message, PasswordChangedDto.class);

        Long userId = passwordChangedDto.getUserId();

        //REST template kontaktiranje drugog servisa
        ResponseEntity<KorisniciDto> korisnikDto = userServiceApiClient.exchange("/api/korisnici/" + userId, HttpMethod.GET,
                null, KorisniciDto.class);


        TipNotifikacijeDTO tipNotifikacijeDTO = tipNotifikacijeService.getTipoviNotifikacije(passwordChangedDto.getNotificationType());
        MailTekstFormater mailTextFormater = new MailTekstFormater();
        String mailMsg = mailTextFormater.formatirajTekst(tipNotifikacijeDTO.getMessage(), passwordChangedDto);

        String userEmail = korisnikDto.getBody().getEmail();
        emailService.sendSimpleMessage(korisnikDto.getBody().getEmail(), "PASSEWORD_EMAIL", mailMsg);

        NotifikacijeCreateDTO notifikacijeCreateDTO =
                new NotifikacijeCreateDTO(mailMsg,userId,userEmail, Date.valueOf(LocalDate.now()),new TipNotifikacijeDTO("PASSEWORD_EMAIL"));
        notifikacijaService.dodajNotifikaciju(notifikacijeCreateDTO);

    }
}
