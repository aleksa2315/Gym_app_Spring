package org.example.repository;

import org.example.domain.Klijent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KlijentRepository extends JpaRepository<Klijent, Integer> {
    @Query("SELECT k FROM Klijent k WHERE k.korisnik.id = :id")
    Optional<Klijent> findByKorisnikId(@Param("id") Integer id);
}