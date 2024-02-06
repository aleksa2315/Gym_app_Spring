package org.example.repository;

import org.example.domain.Klijent;
import org.example.domain.Korisnici;
import org.example.domain.TipKorisnika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KorisniciRepository extends JpaRepository<Korisnici, Integer> {
      Optional<Korisnici> findByUsernameAndPassword(String username, String password);
      Optional<Korisnici> findByEmailAndPassword(String email, String password);
      Optional<Korisnici> findById(Integer id);
      Optional<Korisnici> findByUsername(String username);
      Optional<Korisnici> findUserByActivationCode(String code);
}