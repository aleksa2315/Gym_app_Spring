package example.mapper;

import example.domen.TerminTreninga;
import example.dto.TerminTreningaDTO;
import example.dto.ZakazaniTerminDTO;
import example.domen.ZakazaniTermin;
import example.repository.TerminTreningaRepository;

import java.util.Optional;

public class ZakazaniTerminMapper {

    private static TerminTreningaRepository terminTreningaRepository;

    public ZakazaniTerminMapper(TerminTreningaRepository terminTreningaRepository) {
        this.terminTreningaRepository = terminTreningaRepository;
    }

    public static ZakazaniTerminDTO toDTO(ZakazaniTermin zakazaniTermin) {
        ZakazaniTerminDTO zakazaniTerminDTO = new ZakazaniTerminDTO();
        zakazaniTerminDTO.setId(zakazaniTermin.getId());
        zakazaniTerminDTO.setIdTermina(zakazaniTermin.getTerminTreninga().getId());
        zakazaniTerminDTO.setKlijentId(zakazaniTermin.getKlijentId());
        zakazaniTerminDTO.setCena(zakazaniTermin.getCena());
        zakazaniTerminDTO.setJeBesplatan(zakazaniTermin.getJeBesplatan());
        TerminTreningaDTO terminTreningaDTO = TerminTreningaMapper.toDTO(zakazaniTermin.getTerminTreninga());
        zakazaniTerminDTO.setTerminTreningaDTO(terminTreningaDTO);
        return zakazaniTerminDTO;
    }

    public static ZakazaniTermin toEntity(ZakazaniTerminDTO zakazaniTerminDTO) {
        ZakazaniTermin zakazaniTermin = new ZakazaniTermin();
        zakazaniTermin.setId(zakazaniTerminDTO.getId());
        zakazaniTermin.setTerminTreninga(ZakazaniTerminMapper.getTerminTreningaById(zakazaniTerminDTO.getIdTermina()));
        zakazaniTermin.setKlijentId(zakazaniTerminDTO.getKlijentId());
        zakazaniTermin.setCena(zakazaniTerminDTO.getCena());
        zakazaniTermin.setJeBesplatan(zakazaniTerminDTO.getJeBesplatan());
        zakazaniTermin.setTerminTreninga(getTerminTreningaById(zakazaniTerminDTO.getIdTermina()));
        return zakazaniTermin;
    }

    private static TerminTreninga getTerminTreningaById(Long id) {
        Optional<TerminTreninga> terminTreningaOptional = terminTreningaRepository.findById(id);
        return terminTreningaOptional.get();
    }

    private TerminTreningaRepository getTerminRepository() {
        return terminTreningaRepository;
    }

}
