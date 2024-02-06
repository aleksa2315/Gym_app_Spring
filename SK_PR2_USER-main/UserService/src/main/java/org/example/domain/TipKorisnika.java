package org.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tip_korisnika")
public class TipKorisnika {
    @Id
    @Column(name = "tip_id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "naziv", nullable = false, length = 20)
    private String naziv;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

}