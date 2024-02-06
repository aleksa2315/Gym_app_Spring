package org.example.domain;

import javax.persistence.*;

@Entity
@Table (name = "zabrane")
public class Zabrane implements java.io.Serializable{

    @Id
    @Column(name = "korisnik_id")
    private Integer korisnikId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "korisnik_id")
    private Korisnici korisnik;

    @Column(name = "zabranjen", nullable = false)
    private boolean zabranjen;

    public Integer getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Integer korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Korisnici getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnici korisnik) {
        this.korisnik = korisnik;
    }

    public Korisnici getKorisnici() {
        return korisnik;
    }

    public void setKorisnici(Korisnici korisnik) {
        this.korisnik = korisnik;
    }

    public boolean isZabranjen() {
        return zabranjen;
    }

    public void setZabranjen(boolean zabranjen) {
        this.zabranjen = zabranjen;
    }
}
