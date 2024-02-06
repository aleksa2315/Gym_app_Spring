package example.mapper;

import example.domen.FiskulturnaSala;
import example.dto.FiskulturnaSalaDTO;

public class FiskulturnaSalaMapper {
    public static FiskulturnaSalaDTO toDTO(FiskulturnaSala sala) {
        FiskulturnaSalaDTO dto = new FiskulturnaSalaDTO();
        dto.setId(sala.getId());
        dto.setIme(sala.getIme());
        dto.setKratakOpis(sala.getKratakOpis());
        dto.setBroj_personalnih_trenera(sala.getBrojPersonalnihTrenera());
        return dto;
    }

    public static FiskulturnaSala toEntity(FiskulturnaSalaDTO dto) {
        FiskulturnaSala sala = new FiskulturnaSala();
        sala.setId(dto.getId());
        sala.setIme(dto.getIme());
        sala.setKratakOpis(dto.getKratakOpis());
        sala.setBrojPersonalnihTrenera(dto.getBroj_personalnih_trenera());
        return sala;
    }
}
