package org.example.service.impl;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javassist.NotFoundException;
import org.example.domain.Klijent;
import org.example.domain.Korisnici;
import org.example.domain.Menadzer;
import org.example.domain.Zabrane;
import org.example.dto.*;
import org.example.helper.MessageHelper;
import org.example.mapper.KorisnikMapper;
import org.example.repository.KlijentRepository;
import org.example.repository.KorisniciRepository;
import org.example.repository.MenadzerRepository;
import org.example.security.service.TokenService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager entityManager;
    private TokenService tokenService;
    private KorisniciRepository userRepository;
    private KorisnikMapper userMapper;
    private KlijentRepository klijentRepository;
    private MenadzerRepository menadzerRepository;
    private MessageHelper messageHelper;
    private JmsTemplate jmsTemplate;
    private String destination;
    private String destinationForPassword;

    public UserServiceImpl(EntityManager entityManager, TokenService tokenService,
                           KorisniciRepository userRepository, KorisnikMapper userMapper,
                           KlijentRepository klijentRepository, MessageHelper messageHelper,
                           MenadzerRepository menadzerRepository,
                           JmsTemplate jmsTemplate,
                           @Value("${destination.createNotification}") String destination,
                           @Value("${destination.passwordChanged}") String destinationForPassword) {
        this.entityManager = entityManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.klijentRepository = klijentRepository;
        this.messageHelper = messageHelper;
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
        this.destinationForPassword = destinationForPassword;
        this.menadzerRepository = menadzerRepository;
    }

    @Override
    public Page<KorisniciDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public KorisniciDto add(KorisniciCreateDto userCreateDto) {
        Korisnici user = userMapper.userCreateDtoToUser(userCreateDto);

        Zabrane zabrane = new Zabrane();
        zabrane.setKorisnik(user);
        zabrane.setKorisnikId(user.getId());
        zabrane.setZabranjen(false);
        user.setZabrane(zabrane);

        Random rnd = new Random();
        String activationCode = String.valueOf(rnd.nextInt(999999));
        user.setActivationCode(activationCode);
        userRepository.save(user);

        Optional<Korisnici> userFromDB = userRepository.findByUsername(userCreateDto.getUsername());
        Long id = Long.valueOf(userFromDB.get().getId());
        String ime = userFromDB.get().getIme();
        String prezime = userFromDB.get().getPrezime();
        String email = userFromDB.get().getEmail();

        ActivationEmailDataDto activationEmailDataDto = new ActivationEmailDataDto("ACTIVATION_EMAIL",ime,prezime,id,email);
        activationEmailDataDto.setActivationCode(activationCode);
        activationEmailDataDto.setActivationLink("http://localhost:8084/users/api/korisnici/verifikuj/"+activationCode);
        jmsTemplate.convertAndSend(destination, messageHelper.createTextMessage(activationEmailDataDto));

        return userMapper.userToUserDto(user);
    }

    @Override
    public String verifikujKorisnika(String kod) {

        Korisnici korisnici = null;
        try {
            korisnici = userRepository.findUserByActivationCode(kod).orElseThrow(() -> new NotFoundException("Invalid activation code"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        korisnici.setActivationCode("Verifikovan");

        userRepository.save(korisnici);

        return "Successfully verified";
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) throws NotFoundException {
        System.out.println(tokenRequestDto.getEmail());
        System.out.println(tokenRequestDto.getPassword());
        //Try to find active user for specified credentials
        Korisnici user = userRepository
                .findByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with email: %s and password: %s not found.", tokenRequestDto.getEmail(),
                                tokenRequestDto.getPassword())));


        if(proveriZabranu(user.getId())){
            return new TokenResponseDto("Zabranjen pristup");
        }
        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("tip_korisnika", user.getTipKorisnika().getNaziv());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }
    @Override
    public boolean proveriZabranu(Integer korisnik_id) {
        try {
            String jpql = "SELECT z.zabranjen FROM Zabrane z WHERE z.korisnikId = :korisnik_id";
            Boolean zabranjenValue = entityManager.createQuery(jpql, Boolean.class)
                    .setParameter("korisnik_id", korisnik_id)
                    .getSingleResult();
            return zabranjenValue;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public KorisniciDto getUser(Integer id) {
        Klijent klijent = klijentRepository.findByKorisnikId(id).orElse(null);
        Korisnici korisnik = userRepository.findById(id).orElse(null);
        KorisniciDto korisniciDto = new KorisniciDto();
        korisniciDto.setEmail(korisnik.getEmail());
        korisniciDto.setIme(korisnik.getIme());
        korisniciDto.setPrezime(korisnik.getPrezime());
        korisniciDto.setDatumRodjenja(korisnik.getDatumRodjenja());
        korisniciDto.setUsername(korisnik.getUsername());
        korisniciDto.setPassword(korisnik.getPassword());
        return korisniciDto;
    }

    @Override
    public KorisniciDto findClientById(Long id) {
        try {
            return userRepository.findById(Math.toIntExact(id))
                    .map(userMapper::userToUserDto)
                    .orElseThrow(() -> new NotFoundException(String.format("Korisnik sa idem: %d nije pronadjen.", id)));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getImeSale(Long idMenadzera) {
        return menadzerRepository.findByKorisnik_id(Math.toIntExact(idMenadzera)).get().getSalaNaziv();
    }

    @Override
    public void azurirajImeSale(String imeSale, String token) {
        Long id = tokenService.parseId(token);
        Menadzer menadzer = menadzerRepository.findByKorisnik_id(Math.toIntExact(id)).get();
        menadzer.setSalaNaziv(imeSale);
        menadzerRepository.save(menadzer);
    }

    @Override
    public void povecajBrojTreninga(Integer id) {
        Klijent klijent = klijentRepository.findByKorisnikId(id).get();
        klijent.setZakazaniTreninzi(klijent.getZakazaniTreninzi() + 1);
        klijentRepository.save(klijent);
    }

    @Override
    public void smanjiBrojTreninga(Integer id) {
        Klijent klijent = klijentRepository.findByKorisnikId(id).get();
        klijent.setZakazaniTreninzi(klijent.getZakazaniTreninzi() - 1);
        klijentRepository.save(klijent);
    }

    @Override
    public String getIdMenadzera(String imeSale) {
        Menadzer menadzer = menadzerRepository.findBySalaNaziv(imeSale);
        return String.valueOf(menadzer.getKorisnik().getId());
    }
}
