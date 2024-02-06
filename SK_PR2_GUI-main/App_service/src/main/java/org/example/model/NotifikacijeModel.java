package org.example.model;

import org.example.restClient.dto.KorisniciDto;
import org.example.restClient.dto.KorisniciListaDto;
import org.example.restClient.dto.NotifikacijeDto;
import org.example.restClient.dto.NotifikacijeListaDto;

import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

public class NotifikacijeModel extends DefaultTableModel {
    private NotifikacijeListaDto notifikacijeListaDto = new NotifikacijeListaDto();

    public NotifikacijeModel(){
        super(new String[]{"datum_slanja","email", "korisnik_id", "message", "tip_notifikacije_id"},0);
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
        NotifikacijeDto dto = new NotifikacijeDto();
        dto.setDatumSlanja((LocalDate) row[0]);
        dto.setEmail((String) row[1]);
        dto.setKorisnikId((long) row[2]);
        dto.setMessage((String) row[3]);
        dto.setTipNotifikacije((String) row[4]);

        notifikacijeListaDto.getContent().add(dto);
    }

    public NotifikacijeListaDto getNotifikacijeListaDto() {
        return notifikacijeListaDto;
    }

    public void setNotifikacijeListaDto(NotifikacijeListaDto notifikacijeListaDto) {
        this.notifikacijeListaDto = notifikacijeListaDto;
    }
}
