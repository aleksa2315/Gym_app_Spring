package org.example.model;

import org.example.restClient.dto.TerminTreningaDto;
import org.example.restClient.dto.TerminTreningaListDto;
import org.example.restClient.dto.ZakazaniTerminDTO;
import org.example.restClient.dto.ZakazaniTerminListaDto;

import javax.swing.table.DefaultTableModel;
import java.sql.Time;
import java.util.Date;

public class ZakazaniTableModel extends DefaultTableModel {

    private ZakazaniTerminListaDto zakazaniTerminListaDto = new ZakazaniTerminListaDto();

    public ZakazaniTableModel(){
        super(new String[]{"id", "nazivSale", "cena", "klijent_id", "datum", "vreme_pocetka"},0);
    }

    public void removeRows(){
        if (this.getRowCount() > 0) {
            for (int i = this.getRowCount() - 1; i > -1; i--) {
                this.removeRow(i);
            }
        }
    }

    @Override
    public void addRow(Object[] row) {
        super.addRow(row);
        ZakazaniTerminDTO dto = new ZakazaniTerminDTO();
        dto.setId(Long.parseLong(String.valueOf(row[0])));
        TerminTreningaDto terminTreningaDto = new TerminTreningaDto();
        terminTreningaDto.setNazivSale(String.valueOf(row[1]));
        dto.setCena(Integer.parseInt(String.valueOf(row[2])));
        dto.setKlijentId(Integer.parseInt(String.valueOf(row[3])));
        terminTreningaDto.setDatum((Date) row[4]);
        terminTreningaDto.setVremePocetka((Time) row[5]);
        dto.setTerminTreningaDto(terminTreningaDto);
    }

    public ZakazaniTerminListaDto getZakazaniTerminListaDto() {
        return zakazaniTerminListaDto;
    }

    public void setZakazaniTerminListaDto(ZakazaniTerminListaDto zakazaniTerminListaDto) {
        this.zakazaniTerminListaDto = zakazaniTerminListaDto;
    }
}
