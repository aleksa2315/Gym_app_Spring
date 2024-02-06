package org.example.restClient.dto;

import java.sql.Time;
import java.util.Date;

public class TerminTreningaDto {
    private Long id;
    private Long idSale;
    private Long idTreninga;
    private Date datum;
    private Time vremePocetka;
    private Integer maksimalanBrojUcesnika;
    private String nazivSale;
    private String nazivTreninga;
    private Integer cena;

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    private Integer brojUcesnika;

    public Integer getBrojUcesnika() {
        return brojUcesnika;
    }

    public void setBrojUcesnika(Integer brojUcesnika) {
        this.brojUcesnika = brojUcesnika;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSale() {
        return idSale;
    }

    public void setIdSale(Long idSale) {
        this.idSale = idSale;
    }

    public Long getIdTreninga() {
        return idTreninga;
    }

    public void setIdTreninga(Long idTreninga) {
        this.idTreninga = idTreninga;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Time getVremePocetka() {
        return vremePocetka;
    }

    public void setVremePocetka(Time vremePocetka) {
        this.vremePocetka = vremePocetka;
    }

    public Integer getMaksimalanBrojUcesnika() {
        return maksimalanBrojUcesnika;
    }

    public void setMaksimalanBrojUcesnika(Integer maksimalanBrojUcesnika) {
        this.maksimalanBrojUcesnika = maksimalanBrojUcesnika;
    }

    @Override
    public String toString() {
        return "TerminTreningaDto{" +
                "id=" + id +
                ", idSale=" + idSale +
                ", idTreninga=" + idTreninga +
                ", datum=" + datum +
                ", vremePocetka=" + vremePocetka +
                ", maksimalanBrojUcesnika=" + maksimalanBrojUcesnika +
                ", nazivSale='" + nazivSale + '\'' +
                ", nazivTreninga='" + nazivTreninga + '\'' +
                ", brojUcesnika=" + brojUcesnika +
                '}';
    }

    public String getNazivSale() {
        return nazivSale;
    }

    public void setNazivSale(String nazivSale) {
        this.nazivSale = nazivSale;
    }

    public String getNazivTreninga() {
        return nazivTreninga;
    }

    public void setNazivTreninga(String nazivTreninga) {
        this.nazivTreninga = nazivTreninga;
    }
}
