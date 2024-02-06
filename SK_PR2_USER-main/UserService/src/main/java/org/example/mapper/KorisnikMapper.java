package org.example.mapper;

import org.example.domain.Klijent;
import org.example.domain.Korisnici;
import org.example.domain.Menadzer;
import org.example.dto.KorisniciCreateDto;
import org.example.dto.KorisniciDto;
import org.example.repository.KlijentRepository;
import org.example.repository.MenadzerRepository;
import org.example.repository.TipKorisnikaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
public class KorisnikMapper {

    private TipKorisnikaRepository roleRepository;
    private final KlijentRepository klijentRepository;
    private final MenadzerRepository menadzerRepository;

    public KorisnikMapper(TipKorisnikaRepository roleRepository,
                          KlijentRepository klijentRepository, MenadzerRepository menadzerRepository) {
        this.roleRepository = roleRepository;
        this.klijentRepository = klijentRepository;
        this.menadzerRepository = menadzerRepository;
    }

    public KorisniciDto userToUserDto(Korisnici user) {
        KorisniciDto userDto = new KorisniciDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setIme(user.getIme());
        userDto.setPrezime(user.getPrezime());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setDatumRodjenja(user.getDatumRodjenja());
        return userDto;
    }

    public Korisnici userCreateDtoToUser(KorisniciCreateDto userCreateDto) {
        Korisnici user = new Korisnici();

        user.setEmail(userCreateDto.getEmail());
        user.setIme(userCreateDto.getIme());
        user.setPrezime(userCreateDto.getPrezime());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        //Ovo je bilo zaboravljeno
        user.setDatumRodjenja(userCreateDto.getDatumRodjenja());

        user.setTipKorisnika(roleRepository.findByNaziv(userCreateDto.getTipKorisnikaNaziv()).get());


        if (userCreateDto.getTipKorisnikaNaziv().equalsIgnoreCase("klijent")){
            if (user.getKlijent() == null) {
                user.setKlijent(new Klijent());
            }
        }else if(userCreateDto.getTipKorisnikaNaziv().equalsIgnoreCase("menadzer")){
            if (user.getMenadzer() == null) {
                user.setMenadzer(new Menadzer());
            }
        }


        if ("KLIJENT".equals(userCreateDto.getTipKorisnikaNaziv())) {

            Random random = new Random();
            int randomInt = 10000000 + random.nextInt(90000000);

            user.getKlijent().setClanskaKarta(String.valueOf(randomInt));
            user.getKlijent().setZakazaniTreninzi(0);

        } else if ("MENADZER".equals(userCreateDto.getTipKorisnikaNaziv())) {

            user.getMenadzer().setDatumZaposljavanja(LocalDate.now());
            user.getMenadzer().setSalaNaziv("a");

        }
        return user;
    }
}
