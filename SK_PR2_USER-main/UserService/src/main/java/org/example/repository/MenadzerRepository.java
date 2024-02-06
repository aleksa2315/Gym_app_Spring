package org.example.repository;

import org.example.domain.Menadzer;
import org.example.dto.MenadzerDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenadzerRepository extends JpaRepository<Menadzer, Integer> {
    Optional<Menadzer> findByKorisnik_id(Integer korisnik_id);

    Menadzer findBySalaNaziv(String imeSale);
}
