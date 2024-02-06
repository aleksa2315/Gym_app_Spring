package example.listener;

import example.dto.*;
import example.helper.MailTekstFormater;
import example.helper.MessageHelper;
import example.service.impl.EmailServiceImpl;
import example.service.NotifikacijaService;
import example.service.TipNotifikacijeService;
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
public class AktivacioniMail {
    private MessageHelper messageHelper;
    private NotifikacijaService notifikacijaService;
    private TipNotifikacijeService tipNotifikacijeService;
    private EmailServiceImpl emailService;
    private RestTemplate userServiceApiClient;


    public AktivacioniMail(MessageHelper messageHelper, NotifikacijaService notifikacijaService,
                           EmailServiceImpl emailService, RestTemplate userServiceApiClient, TipNotifikacijeService tipNotifikacijeService) {
        this.messageHelper = messageHelper;
        this.notifikacijaService = notifikacijaService;
        this.emailService = emailService;
        this.userServiceApiClient = userServiceApiClient;
        this.tipNotifikacijeService = tipNotifikacijeService;
    }

    @JmsListener(destination = "${destination.createNotification}", concurrency = "5-10")
    public void addNotifAndSendMail(Message message) throws JMSException, IllegalAccessException, NotFoundException {
        AktivacioniMailDTO aktivacioniMailDTO = messageHelper.getMessage(message, AktivacioniMailDTO.class);
        System.out.println("Nova notifikacia");

        Long userId = aktivacioniMailDTO.getId();
        String userEmail = aktivacioniMailDTO.getEmail();

        ResponseEntity<KorisniciDto> korisnikDto = userServiceApiClient.exchange("/api/korisnici/" + userId.toString(), HttpMethod.GET,
                null, KorisniciDto.class);


        TipNotifikacijeDTO tipNotifikacijeDTO = tipNotifikacijeService.getTipoviNotifikacije(aktivacioniMailDTO.getTipNotifikacije());
        MailTekstFormater mailTekstFormater = new MailTekstFormater();
        System.out.println(tipNotifikacijeDTO.getMessage());
        String mailMsg = mailTekstFormater.formatirajTekst(tipNotifikacijeDTO.getMessage(), aktivacioniMailDTO);

        emailService.sendSimpleMessage(korisnikDto.getBody().getEmail(), "ACTIVATION_EMAIL", mailMsg + "\n" + aktivacioniMailDTO.getActivationLink());

        NotifikacijeCreateDTO notifikacijeCreateDTO =
                new NotifikacijeCreateDTO(mailMsg,userId,userEmail, Date.valueOf(LocalDate.now()),new TipNotifikacijeDTO("ACTIVATION_EMAIL"));
        notifikacijaService.dodajNotifikaciju(notifikacijeCreateDTO);
    }
}