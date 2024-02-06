package example.dto;

public class TrainingScheduleDto {
    private Long korisnikId;
    private String tipNotifikacije;
    private String ime;
    private String prezime;
    private String tipTreninga;
    private String grupaTreninga;
    private String brojTermina;
    private String nazivSale;

    public String getNazivSale() {
        return nazivSale;
    }

    public void setNazivSale(String nazivSale) {
        this.nazivSale = nazivSale;
    }

    public Long getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Long korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getTipNotifikacije() {
        return tipNotifikacije;
    }

    public void setTipNotifikacije(String tipNotifikacije) {
        this.tipNotifikacije = tipNotifikacije;
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

    public String getTipTreninga() {
        return tipTreninga;
    }

    public void setTipTreninga(String tipTreninga) {
        this.tipTreninga = tipTreninga;
    }

    public String getGrupaTreninga() {
        return grupaTreninga;
    }

    public void setGrupaTreninga(String grupaTreninga) {
        this.grupaTreninga = grupaTreninga;
    }

    public String getBrojTermina() {
        return brojTermina;
    }

    public void setBrojTermina(String brojTermina) {
        this.brojTermina = brojTermina;
    }

    @Override
    public String toString() {
        return "TrainingScheduleDto{" +
                "korisnikId=" + korisnikId +
                ", tipNotifikacije='" + tipNotifikacije + '\'' +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", tipTreninga='" + tipTreninga + '\'' +
                ", grupaTreninga='" + grupaTreninga + '\'' +
                ", brojTermina='" + brojTermina + '\'' +
                '}';
    }
}
