package example.repository;

import example.domain.TipNotifikacije;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipNotifikacijeRepository extends JpaRepository<TipNotifikacije, Long> {
    Optional<TipNotifikacije> findNotificationTypeByType(String tip);
}
