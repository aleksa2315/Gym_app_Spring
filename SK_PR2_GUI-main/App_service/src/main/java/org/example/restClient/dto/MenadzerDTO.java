package org.example.restClient.dto;

import java.time.LocalDate;

public class MenadzerDTO extends KorisniciDto {
    private String salaNaziv;
    private LocalDate datumZaposljavanja;

    public String getSalaNaziv() {
        return salaNaziv;
    }

    public void setSalaNaziv(String salaNaziv) {
        this.salaNaziv = salaNaziv;
    }

    public LocalDate getDatumZaposljavanja() {
        return datumZaposljavanja;
    }

    public void setDatumZaposljavanja(LocalDate datumZaposljavanja) {
        this.datumZaposljavanja = datumZaposljavanja;
    }
}