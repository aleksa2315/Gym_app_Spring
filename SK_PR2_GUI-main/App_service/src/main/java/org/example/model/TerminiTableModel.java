package org.example.model;

import org.example.restClient.dto.TerminTreningaDto;
import org.example.restClient.dto.TerminTreningaListDto;

import javax.swing.table.DefaultTableModel;
import java.sql.Time;
import java.util.Date;

public class TerminiTableModel extends DefaultTableModel {

    private TerminTreningaListDto terminTreningaListDto = new TerminTreningaListDto();

    public TerminiTableModel(){
            super(new String[]{"Fiskulturna sala", "Tip treninga", "Datum", "Vreme pocetka", "Maksimalni broj ucesnika"},0);
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
        TerminTreningaDto dto = new TerminTreningaDto();
        dto.setNazivSale(String.valueOf(row[0]));
        dto.setNazivTreninga(String.valueOf(row[1]));
        dto.setDatum((Date) row[2]);
        dto.setVremePocetka((Time) row[3]);
        dto.setMaksimalanBrojUcesnika(Integer.parseInt(String.valueOf(row[4])));
    }

    public TerminTreningaListDto getTerminTreningaListDto() {
        return terminTreningaListDto;
    }

    public void setTerminTreningaListDto(TerminTreningaListDto terminTreningaListDto) {
        this.terminTreningaListDto = terminTreningaListDto;
    }
}
