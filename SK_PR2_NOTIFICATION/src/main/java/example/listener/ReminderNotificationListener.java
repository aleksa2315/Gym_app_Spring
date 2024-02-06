package example.listener;

import example.dto.KorisniciDto;
import example.dto.NotifikacijeCreateDTO;
import example.dto.ReminderNotificationDto;
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
public class ReminderNotificationListener {
    private MessageHelper messageHelper;
    private NotifikacijaService notifikacijaService;
    private TipNotifikacijeService tipNotifikacijeService;
    private EmailServiceImpl emailService;
    private RestTemplate userServiceApiClient;


    public ReminderNotificationListener(MessageHelper messageHelper, NotifikacijaService notifikacijaService,
                           EmailServiceImpl emailService, RestTemplate userServiceApiClient, TipNotifikacijeService tipNotifikacijeService) {
        this.messageHelper = messageHelper;
        this.notifikacijaService = notifikacijaService;
        this.emailService = emailService;
        this.userServiceApiClient = userServiceApiClient;
        this.tipNotifikacijeService = tipNotifikacijeService;
    }

    @JmsListener(destination = "${destination.reminderNotification}", concurrency = "5-10")
    public void addNotifAndSendMail(Message message) throws JMSException, IllegalAccessException, NotFoundException {
        ReminderNotificationDto reminderNotificationDto = messageHelper.getMessage(message, ReminderNotificationDto.class);
        System.out.println("Nova notifikacia");

        Long userId = reminderNotificationDto.getUserId();

        ResponseEntity<KorisniciDto> clientDto = userServiceApiClient.exchange("/api/korisnici/" + userId, HttpMethod.GET,
                null, KorisniciDto.class);


        TipNotifikacijeDTO tipNotifikacijeDTO = tipNotifikacijeService.getTipoviNotifikacije(reminderNotificationDto.getNotificationType());
        MailTekstFormater mailTextFormater = new MailTekstFormater();
        String mailMsg = mailTextFormater.formatirajTekst(tipNotifikacijeDTO.getMessage(), reminderNotificationDto);
        emailService.sendSimpleMessage(clientDto.getBody().getEmail(), "REMINDER_EMAIL", mailMsg);

        NotifikacijeCreateDTO createNotificationDto =
                new NotifikacijeCreateDTO(mailMsg,userId,clientDto.getBody().getEmail(), Date.valueOf(LocalDate.now()),new TipNotifikacijeDTO("REMINDER_EMAIL"));
        notifikacijaService.dodajNotifikaciju(createNotificationDto);

    }
}