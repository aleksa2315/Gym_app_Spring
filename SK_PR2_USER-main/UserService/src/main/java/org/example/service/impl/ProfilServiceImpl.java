package org.example.service.impl;

import javassist.NotFoundException;
import org.example.domain.Korisnici;
import org.example.dto.KorisniciDto;
import org.example.dto.PasswordChangedEmailDTO;
import org.example.helper.MessageHelper;
import org.example.repository.KorisniciRepository;
import org.example.service.ProfilService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProfilServiceImpl implements ProfilService {
    private final KorisniciRepository korisniciRepository;
    private JmsTemplate jmsTemplate;
    private String destinationForPassword;
    private MessageHelper messageHelper;


    public ProfilServiceImpl(KorisniciRepository korisniciRepository, JmsTemplate jmsTemplate,
                             @Value("${destination.passwordChanged}") String destinationForPassword,
                             MessageHelper messageHelper) {
        this.korisniciRepository = korisniciRepository;
        this.jmsTemplate = jmsTemplate;
        this.destinationForPassword = destinationForPassword;
        this.messageHelper = messageHelper;
    }

    public KorisniciDto azurirajProfil(Integer korisnikId, KorisniciDto updateDto) throws NotFoundException {
        Korisnici korisnik = korisniciRepository.findById(korisnikId)
                .orElseThrow(() -> new NotFoundException("Korisnik nije pronađen"));

        korisnik.setDatumRodjenja(updateDto.getDatumRodjenja());
        korisnik.setUsername(updateDto.getUsername());
        korisnik.setPassword(updateDto.getPassword());
        korisnik.setEmail(updateDto.getEmail());
        korisnik.setIme(updateDto.getIme());
        korisnik.setPrezime(updateDto.getPrezime());

        korisniciRepository.save(korisnik);

        KorisniciDto korisniciDto = new KorisniciDto();
        korisniciDto.setId(korisnik.getId());
        korisniciDto.setDatumRodjenja(korisnik.getDatumRodjenja());
        korisniciDto.setUsername(korisnik.getUsername());
        korisniciDto.setPassword(korisnik.getPassword());
        korisniciDto.setEmail(korisnik.getEmail());
        korisniciDto.setIme(korisnik.getIme());
        korisniciDto.setPrezime(korisnik.getPrezime());

        return korisniciDto;
    }

    @Override
    public KorisniciDto promeniLozinku(Integer id, KorisniciDto updateDto) {
        Korisnici korisnik = korisniciRepository.findById(id).get();

        korisnik.setPassword(updateDto.getPassword());
        System.out.println("Promenjena lozinka");
        korisniciRepository.save(korisnik);

        PasswordChangedEmailDTO passwordChangedEmailDTO = new PasswordChangedEmailDTO();
        passwordChangedEmailDTO.setNotificationType("PASSEWORD_EMAIL");
        passwordChangedEmailDTO.setFirstName(korisnik.getIme());
        passwordChangedEmailDTO.setLastName(korisnik.getPrezime());
        passwordChangedEmailDTO.setPassword(updateDto.getPassword());
        passwordChangedEmailDTO.setUserId(Long.valueOf(id));

        jmsTemplate.convertAndSend(destinationForPassword, messageHelper.createTextMessage(passwordChangedEmailDTO));
        return null;
    }
}

