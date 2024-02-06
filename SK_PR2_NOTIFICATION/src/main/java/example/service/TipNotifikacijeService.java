package example.service;

import example.dto.TipNotifikacijeDTO;
import javassist.NotFoundException;

public interface TipNotifikacijeService {
    TipNotifikacijeDTO dodajTipNotifikacije(TipNotifikacijeDTO notificationTypeDto);

    TipNotifikacijeDTO getTipoviNotifikacije(String type) throws NotFoundException;

    TipNotifikacijeDTO getTipNotifikacije(Long id);
}
