package org.example.restClient.dto;

public class ZakazaniTerminDTO {

    private Long id;
    private TerminTreningaDto terminTreningaDto;
    private Integer klijentId;
    private Integer cena;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TerminTreningaDto getTerminTreningaDto() {
        return terminTreningaDto;
    }

    public void setTerminTreningaDto(TerminTreningaDto terminTreningaDto) {
        this.terminTreningaDto = terminTreningaDto;
    }

    public Integer getKlijentId() {
        return klijentId;
    }

    public void setKlijentId(Integer klijentId) {
        this.klijentId = klijentId;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }


    @Override
    public String toString() {
        return "ZakazaniTerminDTO{" +
                "id=" + id +
                ", terminTreningaDto=" + terminTreningaDto +
                ", klijentId=" + klijentId +
                ", cena=" + cena +
                '}';
    }
}
