package org.example.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "klijent")
public class Klijent implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "korisnik_id")
    private Korisnici korisnik;

    @Size(max = 255)
    @NotNull
    @Column(name = "clanska_karta", nullable = false)
    @GeneratedValue
    private String clanskaKarta;

    @Column(name = "zakazani_treninzi", nullable = false)
    private Integer zakazaniTreninzi;

    public Korisnici getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnici korisnik) {
        this.korisnik = korisnik;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}