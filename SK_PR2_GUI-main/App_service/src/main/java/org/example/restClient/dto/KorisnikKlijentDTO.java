package org.example.restClient.dto;

import java.time.LocalDate;
import java.util.Date;

public class KorisnikKlijentDTO {
    private String username;
    private String email;

    private Date datumRodjenja;

    private String ime;

    private String prezime;

    private String clanskaKarta;
    private Integer zakazaniTreninzi;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getClanskaKarta() {
        return clanskaKarta;
    }

    public void setClanskaKarta(String clanskaKarta) {
        this.clanskaKarta = clanskaKarta;
    }

    public Integer getZakazaniTreninzi() {
        return zakazaniTreninzi;
    }

    public void setZakazaniTreninzi(Integer zakazaniTreninzi) {
        this.zakazaniTreninzi = zakazaniTreninzi;
    }



    @Override
    public String toString() {
        return "KorisnikKlijentDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", datumRodjenja=" + datumRodjenja +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", clanskaKarta='" + clanskaKarta + '\'' +
                ", zakazaniTreninzi=" + zakazaniTreninzi +
                '}';
    }
}
