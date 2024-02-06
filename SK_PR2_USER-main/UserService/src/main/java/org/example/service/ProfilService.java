package org.example.service;

import io.swagger.models.auth.In;
import javassist.NotFoundException;
import org.example.dto.KorisniciDto;
import org.springframework.stereotype.Service;

public interface ProfilService {
    KorisniciDto azurirajProfil(Integer korisnikId, KorisniciDto updateDto) throws NotFoundException;

    KorisniciDto promeniLozinku(Integer id, KorisniciDto updateDto);
}
