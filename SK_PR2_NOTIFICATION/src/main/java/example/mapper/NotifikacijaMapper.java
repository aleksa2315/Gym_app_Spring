package example.mapper;

import example.domain.Notifikacija;
import example.dto.NotifikacijaDTO;
import example.dto.NotifikacijeCreateDTO;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Component;

@Component
public class NotifikacijaMapper {

    private TipNotifikacijeMapper tipNotifikacijeMapper;

    public NotifikacijaMapper(TipNotifikacijeMapper tipNotifikacijeMapper) {
        this.tipNotifikacijeMapper = tipNotifikacijeMapper;
    }

    public NotifikacijaDTO notifikacijaToNotifikacijaDTO(Notifikacija notifikacija) {
        NotifikacijaDTO notifikacijaDTO = new NotifikacijaDTO();
        notifikacijaDTO.setId(notifikacija.getId());
        notifikacijaDTO.setMessage(notifikacija.getMessage());
        notifikacijaDTO.setKorisnikId(notifikacija.getKorisnikId());
        notifikacijaDTO.setEmail(notifikacija.getEmail());
        notifikacijaDTO.setDatumSlanja(notifikacija.getDatumSlanja());

        //Mapiranje TipNotifikacije u DTO
        notifikacijaDTO.setTipNotifikacije(tipNotifikacijeMapper.tipNotifikacijetoTipNotifikacijeDTO(notifikacija.getTipNotifikacije()));

        return notifikacijaDTO;
    }

    public Notifikacija notifikacijaDTOtoNotifikacija(NotifikacijaDTO notifikacijaDTO) {
        Notifikacija notifikacija = new Notifikacija();
        notifikacija.setId(notifikacijaDTO.getId());
        notifikacija.setMessage(notifikacijaDTO.getMessage());
        notifikacija.setKorisnikId(notifikacijaDTO.getKorisnikId());
        notifikacija.setEmail(notifikacijaDTO.getEmail());
        notifikacija.setDatumSlanja(notifikacijaDTO.getDatumSlanja());

        //Mapiranje DTO objekta TipNotifikacije u entitet TipNotifikacije
        notifikacija.setTipNotifikacije(tipNotifikacijeMapper.tipNotifikacijeDTOtoTipNotifikacije(notifikacijaDTO.getTipNotifikacije()));

        return notifikacija;
    }

    public Notifikacija createNotifikacijaDTOToNotifikacije(NotifikacijeCreateDTO notifikacijeCreateDTO)
    {
        Notifikacija notifikacija = new Notifikacija();
        notifikacija.setMessage(notifikacijeCreateDTO.getMessage());
        notifikacija.setKorisnikId(notifikacijeCreateDTO.getKorisnikId());
        notifikacija.setEmail(notifikacijeCreateDTO.getEmail());
        notifikacija.setDatumSlanja(notifikacijeCreateDTO.getDatumSlanja());

        //Mapiranje DTO objekta TipNotifikacije u entitet TipNotifikacije
        notifikacija.setTipNotifikacije(tipNotifikacijeMapper.tipNotifikacijeDTOtoTipNotifikacije(notifikacijeCreateDTO.getTipNotifikacije()));

        return notifikacija;
    }
}
