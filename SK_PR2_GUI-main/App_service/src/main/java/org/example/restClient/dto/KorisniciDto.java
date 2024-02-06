package org.example.restClient.dto;



import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;


public class KorisniciDto{
    private Integer id;

    private String username;

    private String password;

    private String email;

    private LocalDate datumRodjenja;

    private String ime;

    private String prezime;
    private boolean zabranjenPristup;

    public boolean isZabranjenPristup() {
        return zabranjenPristup;
    }

    public void setZabranjenPristup(boolean zabranjenPristup) {
        this.zabranjenPristup = zabranjenPristup;
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



    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
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

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KorisniciDto entity = (KorisniciDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.username, entity.username) &&
                Objects.equals(this.password, entity.password) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.datumRodjenja, entity.datumRodjenja) &&
                Objects.equals(this.ime, entity.ime) &&
                Objects.equals(this.prezime, entity.prezime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, datumRodjenja, ime, prezime);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "username = " + username + ", " +
                "password = " + password + ", " +
                "email = " + email + ", " +
                "datumRodjenja = " + datumRodjenja + ", " +
                "ime = " + ime + ", " +
                "prezime = " + prezime + ")";
    }
}