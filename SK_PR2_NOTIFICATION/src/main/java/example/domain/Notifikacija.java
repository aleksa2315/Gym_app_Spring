package example.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "notifikacija")
public class Notifikacija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 255)
    private String message;
    @NotNull
    private long korisnikId;
    @NotNull
    @Size(max = 255)
    @Email
    private String email;
    @NotNull
    private Date datumSlanja;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tip_notifikacije_id", nullable = false)
    private TipNotifikacije tipNotifikacije;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(long korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDatumSlanja() {
        return datumSlanja;
    }

    public void setDatumSlanja(Date datumSlanja) {
        this.datumSlanja = datumSlanja;
    }

    public TipNotifikacije getTipNotifikacije() {
        return tipNotifikacije;
    }

    public void setTipNotifikacije(TipNotifikacije tipNotifikacije) {
        this.tipNotifikacije = tipNotifikacije;
    }
}
