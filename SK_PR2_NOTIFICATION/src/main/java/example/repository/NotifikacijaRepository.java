package example.repository;

import example.domain.Notifikacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotifikacijaRepository extends JpaRepository<Notifikacija,Long> {
    Optional<Notifikacija> findNotifikacijaById(Long id);

    List<Notifikacija> findNotifikacijaByKorisnikId(Long id);

    @Query(value = "SELECT * FROM SK_NOTIFICATION.notifikacija", nativeQuery = true)
    List<Notifikacija> getAllNotifikacije();
}
