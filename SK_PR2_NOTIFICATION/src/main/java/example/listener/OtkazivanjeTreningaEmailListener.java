package example.listener;

import example.dto.KorisniciDto;
import example.dto.NotifikacijeCreateDTO;
import example.dto.TipNotifikacijeDTO;
import example.dto.TrainingScheduleDto;
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
public class OtkazivanjeTreningaEmailListener {
    private MessageHelper messageHelper;

    private NotifikacijaService notifikacijaService;
    private TipNotifikacijeService tipNotifikacijeService;
    private EmailServiceImpl emailService;
    private RestTemplate userServiceApiClient;
    private RestTemplate gymServiceApiClient;


    public OtkazivanjeTreningaEmailListener(MessageHelper messageHelper, NotifikacijaService notifikacijaService,
                                            EmailServiceImpl emailService, RestTemplate userServiceApiClient, TipNotifikacijeService tipNotifikacijeService, RestTemplate gymServiceApiClient) {
        this.messageHelper = messageHelper;
        this.notifikacijaService = notifikacijaService;
        this.emailService = emailService;
        this.userServiceApiClient = userServiceApiClient;
        this.tipNotifikacijeService = tipNotifikacijeService;
        this.gymServiceApiClient = gymServiceApiClient;
    }

    @JmsListener(destination = "${destination.otkazivanjeTreninga}", concurrency = "5-10")
    void sendScheduleNotificationEmail(Message message) throws JMSException, IllegalAccessException, NotFoundException {
        TrainingScheduleDto trainingScheduleDto = messageHelper.getMessage(message, TrainingScheduleDto.class);
        System.out.println("Zakazivanje treninga notifikacija");
        System.out.println(trainingScheduleDto.getTipNotifikacije());

        Long userId = trainingScheduleDto.getKorisnikId();

        ResponseEntity<KorisniciDto> korisnikDto = userServiceApiClient.exchange("/api/korisnici/" + userId, HttpMethod.GET,
                null, KorisniciDto.class);

        String response = userServiceApiClient.exchange("/api/korisnici/getIdMenadzera/" + trainingScheduleDto.getNazivSale(), HttpMethod.GET, null, String.class).getBody();
        assert response != null;
        Integer managerID = Integer.parseInt(response);

        ResponseEntity<KorisniciDto> managerDto = userServiceApiClient.exchange("/api/korisnici/" + managerID, HttpMethod.GET, null, KorisniciDto.class);

        String managerEmail = managerDto.getBody().getEmail();
        String managerFirstName = managerDto.getBody().getIme();
        String managerLastName = managerDto.getBody().getPrezime();

        trainingScheduleDto.setIme(managerFirstName);
        trainingScheduleDto.setPrezime(managerLastName);


        String clientEmail = korisnikDto.getBody().getEmail();
        String firstName = korisnikDto.getBody().getIme();
        String lastName = korisnikDto.getBody().getPrezime();

        trainingScheduleDto.setIme(firstName);
        trainingScheduleDto.setPrezime(lastName);

        TipNotifikacijeDTO notificationTypeDto = tipNotifikacijeService.getTipoviNotifikacije(trainingScheduleDto.getTipNotifikacije());
        MailTekstFormater mailTextFormater = new MailTekstFormater();
        String mailMsg = mailTextFormater.formatirajTekst(notificationTypeDto.getMessage(), trainingScheduleDto);
        String type = trainingScheduleDto.getTipTreninga();

        emailService.sendSimpleMessage(clientEmail, type, mailMsg);
        emailService.sendSimpleMessage(managerEmail, type, "Otkazan je trening za korisnika " + firstName + " " + lastName + " u sali " + trainingScheduleDto.getNazivSale() + " u ");


        NotifikacijeCreateDTO createNotificationDto =
                new NotifikacijeCreateDTO(mailMsg, userId, clientEmail, Date.valueOf(LocalDate.now()), new TipNotifikacijeDTO(trainingScheduleDto.getTipNotifikacije()));
        notifikacijaService.dodajNotifikaciju(createNotificationDto);

        NotifikacijeCreateDTO create = new NotifikacijeCreateDTO("Otkazan je trening za korisnika " + firstName + " " + lastName + " u sali " + trainingScheduleDto.getNazivSale() + " u ", Long.valueOf(managerID), managerEmail, Date.valueOf(LocalDate.now()), new TipNotifikacijeDTO(trainingScheduleDto.getTipNotifikacije()));
        notifikacijaService.dodajNotifikaciju(create);
    }
}
