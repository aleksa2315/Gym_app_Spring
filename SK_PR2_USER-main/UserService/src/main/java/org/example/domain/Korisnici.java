package org.example.domain;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "korisnici")
public class Korisnici implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @NotNull
    @Column(name = "datum_rodjenja", nullable = false)
    private LocalDate datumRodjenja;

    @Size(max = 255)
    @NotNull
    @Column(name = "ime", nullable = false)
    private String ime;

    @Size(max = 255)
    @NotNull
    @Column(name = "prezime", nullable = false)
    private String prezime;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tip_korisnika_id", nullable = false)
    private TipKorisnika tipKorisnika;

    @OneToOne(mappedBy = "korisnik", cascade = CascadeType.ALL)
    private Klijent klijent;

    @OneToOne(mappedBy = "korisnik", cascade = CascadeType.ALL)
    private Menadzer menadzer;

    @OneToOne(mappedBy = "korisnik", cascade = CascadeType.ALL)
    private Zabrane zabrane;
    private String activationCode;

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
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

    public TipKorisnika getTipKorisnika() {
        return tipKorisnika;
    }

    public void setTipKorisnika(TipKorisnika tipKorisnika) {
        this.tipKorisnika = tipKorisnika;
    }

    public Klijent getKlijent() {
        return klijent;
    }

    public void setKlijent(Klijent klijent) {

        //this.klijent = klijent;

        this.klijent = klijent;
        if (klijent != null) {
            klijent.setKorisnik(this);
        }
    }

    public Menadzer getMenadzer() {
        return menadzer;
    }

    public void setMenadzer(Menadzer menadzer) {
        this.menadzer = menadzer;

        if (menadzer != null) {
            menadzer.setKorisnik(this);
        }
    }

    public Zabrane getZabrane() {
        return zabrane;
    }

    public void setZabrane(Zabrane zabrane) {
        this.zabrane = zabrane;
    }
}