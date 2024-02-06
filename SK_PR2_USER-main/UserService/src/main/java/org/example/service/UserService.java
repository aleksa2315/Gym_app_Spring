package org.example.service;


import javassist.NotFoundException;
import org.example.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<KorisniciDto> findAll(Pageable pageable);

    KorisniciDto add(KorisniciCreateDto userCreateDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto) throws NotFoundException;

    boolean proveriZabranu(Integer idKorisnika);

    KorisniciDto getUser(Integer id);
    String verifikujKorisnika(String kod);
    KorisniciDto findClientById(Long id);

    String getImeSale(Long idMenadzera);

    void azurirajImeSale(String imeSale, String token);

    void povecajBrojTreninga(Integer id);

    void smanjiBrojTreninga(Integer id);

    String getIdMenadzera(String imeSale);
}
