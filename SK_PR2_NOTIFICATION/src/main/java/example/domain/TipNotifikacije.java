package example.domain;

import javax.persistence.*;

@Entity
@Table(name = "tip_notifikacije")
public class TipNotifikacije {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String embeded_msg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return embeded_msg;
    }

    public void setMessage(String embeded_msg) {
        this.embeded_msg = embeded_msg;
    }
}
