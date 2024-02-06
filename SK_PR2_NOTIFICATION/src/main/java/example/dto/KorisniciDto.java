package example.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class KorisniciDto{
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
    @NotNull
    private LocalDate datumRodjenja;
    @NotNull
    @Size(max = 255)
    private String ime;
    @NotNull
    @Size(max = 255)
    private String prezime;

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