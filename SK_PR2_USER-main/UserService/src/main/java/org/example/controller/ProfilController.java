package org.example.controller;

import javassist.NotFoundException;
import org.example.dto.KorisniciDto;
import org.example.security.service.TokenService;
import org.example.service.ProfilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profil")
public class ProfilController {
    private final ProfilService profilService;
    private TokenService tokenService;

    public ProfilController(ProfilService profilService, TokenService tokenService) {
        this.profilService = profilService;
        this.tokenService = tokenService;
    }

    @PutMapping()
    public ResponseEntity<KorisniciDto> azurirajProfil(@RequestBody @Valid KorisniciDto updateDto, @RequestHeader String authorization) throws NotFoundException {
        KorisniciDto azuriraniProfil = profilService.azurirajProfil(updateDto.getId(), updateDto);
        return new ResponseEntity<>(azuriraniProfil, HttpStatus.OK);
    }

    @PutMapping("/promeni-lozinku")
    public ResponseEntity<KorisniciDto> promeniLozinku(@RequestBody @Valid KorisniciDto updateDto, @RequestHeader String authorization) throws NotFoundException {
        KorisniciDto azuriraniProfil = profilService.promeniLozinku(updateDto.getId(), updateDto);
        return new ResponseEntity<>(azuriraniProfil, HttpStatus.OK);
    }
}
