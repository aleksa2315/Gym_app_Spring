package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.helper.LocalDateDeserializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * DTO for {@link org.example.domain.Korisnici}
 */
public class KorisniciCreateDto {
    private Integer id;
    @NotNull
    @Size(max = 255)
    private String username;
    @NotNull
    @Size(max = 255)
    private String password;
    @NotNull
    @Size(max = 255)
    private String email;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate datumRodjenja;
    @NotNull
    @Size(max = 255)
    private String ime;
    @NotNull
    @Size(max = 255)
    private String prezime;
    private Integer tipKorisnikaId;
    private String tipKorisnikaNaziv;

    public static class KlijentCreateDto extends KorisniciCreateDto {
        @NotNull
        @Size(max = 20)
        private String clanskaKarta;

        @NotNull
        private Integer brojZakazanihTrenutnih;

        public String getClanskaKarta() {
            return clanskaKarta;
        }

        public void setClanskaKarta(String clanskaKarta) {
            this.clanskaKarta = clanskaKarta;
        }

        public Integer getBrojZakazanihTrenutnih() {
            return brojZakazanihTrenutnih;
        }

        public void setBrojZakazanihTrenutnih(Integer brojZakazanihTrenutnih) {
            this.brojZakazanihTrenutnih = brojZakazanihTrenutnih;
        }
    }

    public static class MenadzerCreateDto extends KorisniciCreateDto {
        @NotNull
        @Size(max = 255)
        private String salaNaziv;

        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull
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

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public Integer getTipKorisnikaId() {
        return tipKorisnikaId;
    }

    public String getTipKorisnikaNaziv() {
        return tipKorisnikaNaziv;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setTipKorisnikaId(Integer tipKorisnikaId) {
        this.tipKorisnikaId = tipKorisnikaId;
    }

    public void setTipKorisnikaNaziv(String tipKorisnikaNaziv) {
        this.tipKorisnikaNaziv = tipKorisnikaNaziv;
    }
}