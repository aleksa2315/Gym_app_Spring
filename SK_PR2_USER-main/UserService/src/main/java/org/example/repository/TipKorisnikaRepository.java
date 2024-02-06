package org.example.repository;

import org.example.domain.Korisnici;
import org.example.domain.TipKorisnika;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipKorisnikaRepository extends JpaRepository<TipKorisnika, Integer> {
    Optional<TipKorisnika> findByNaziv(String naziv);
}