package example.dto;

import java.util.Date;

public class NotifikacijaDTO {

    private Long id;
    private String message;
    private long korisnikId;
    private String email;
    private Date datumSlanja;
    private TipNotifikacijeDTO tipNotifikacije;

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

    public TipNotifikacijeDTO getTipNotifikacije() {
        return tipNotifikacije;
    }

    public void setTipNotifikacije(TipNotifikacijeDTO tipNotifikacije) {
        this.tipNotifikacije = tipNotifikacije;
    }
}
