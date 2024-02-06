package example.dto;

public class TipNotifikacijeDTO {

    private Long id;
    private String type;
    private String message;

    public Long getId() {
        return id;
    }

    public TipNotifikacijeDTO() {
    }

    public TipNotifikacijeDTO(String type) {
    	this.type = type;
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
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

