package org.example.model;

import org.example.restClient.dto.KorisniciDto;
import org.example.restClient.dto.KorisniciListaDto;
import org.example.restClient.dto.TerminTreningaDto;
import org.example.restClient.dto.TerminTreningaListDto;

import javax.swing.table.DefaultTableModel;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class KorisniciModel extends DefaultTableModel {

    private KorisniciListaDto korisniciListaDto = new KorisniciListaDto();

    public KorisniciModel(){
        super(new String[]{"id","Ime", "Prezime", "Email", "username", "datum_rodjenja", "zabranjen"},0);
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
        KorisniciDto dto = new KorisniciDto();
        dto.setId(Integer.parseInt(String.valueOf(row[0])));
        dto.setIme(String.valueOf(row[1]));
        dto.setPrezime(String.valueOf(row[2]));
        dto.setEmail((String) row[3]);
        dto.setUsername((String) row[4]);
        dto.setDatumRodjenja((LocalDate) row[5]);
        dto.setZabranjenPristup((boolean) row[6]);

        korisniciListaDto.getContent().add(dto);
    }

    public KorisniciListaDto getKorisniciListaDto() {
        return korisniciListaDto;
    }

    public void setKorisniciListaDto(KorisniciListaDto korisniciListaDto) {
        this.korisniciListaDto = korisniciListaDto;
    }
}
