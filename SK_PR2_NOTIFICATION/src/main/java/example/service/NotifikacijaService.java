package example.service;

import example.dto.NotifikacijaDTO;
import example.dto.NotifikacijeCreateDTO;

import java.util.List;

public interface NotifikacijaService {
    NotifikacijaDTO dodajNotifikaciju(NotifikacijeCreateDTO createNotificationDto);
    List<NotifikacijaDTO> getSveKorisniceNotifikacije(Long id);

    List<NotifikacijaDTO> getSveNotifikacije();
}
