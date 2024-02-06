package org.example.restClient.dto;

import java.time.LocalDate;
import java.util.Date;

public class NotifikacijeDto {
    private Long id;
    private String message;
    private long korisnikId;
    private String email;
    private LocalDate datumSlanja;
    private String tipNotifikacije;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(long korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDatumSlanja() {
        return datumSlanja;
    }

    public void setDatumSlanja(LocalDate datumSlanja) {
        this.datumSlanja = datumSlanja;
    }

    public String getTipNotifikacije() {
        return tipNotifikacije;
    }

    public void setTipNotifikacije(String tipNotifikacije) {
        this.tipNotifikacije = tipNotifikacije;
    }
}
