package example.mapper;

import example.domain.TipNotifikacije;
import example.dto.TipNotifikacijeDTO;
import example.repository.TipNotifikacijeRepository;
import org.springframework.stereotype.Component;

@Component
public class TipNotifikacijeMapper {
    private TipNotifikacijeRepository tipNotifikacijeRepository;

    public TipNotifikacijeMapper(TipNotifikacijeRepository tipNotifikacijeRepository) {
        this.tipNotifikacijeRepository = tipNotifikacijeRepository;
    }

    public TipNotifikacije tipNotifikacijeDTOtoTipNotifikacije(TipNotifikacijeDTO tipNotifikacijeDTO)
    {
        TipNotifikacije tipNotifikacije = new TipNotifikacije();

        tipNotifikacije.setType(tipNotifikacijeDTO.getType());
        tipNotifikacije.setMessage(tipNotifikacijeDTO.getMessage());

        return tipNotifikacije;
    }

    public TipNotifikacijeDTO tipNotifikacijetoTipNotifikacijeDTO(TipNotifikacije tipNotifikacije)
    {
        TipNotifikacijeDTO tipNotifikacijeDTO = new TipNotifikacijeDTO();

        tipNotifikacijeDTO.setType(tipNotifikacije.getType());
        tipNotifikacijeDTO.setMessage(tipNotifikacije.getMessage());

        return tipNotifikacijeDTO;
    }
}
