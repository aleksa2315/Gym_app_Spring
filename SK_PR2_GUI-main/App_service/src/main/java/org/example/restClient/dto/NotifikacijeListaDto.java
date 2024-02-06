package org.example.restClient.dto;

import java.util.ArrayList;
import java.util.List;

public class NotifikacijeListaDto {

    private List<NotifikacijeDto> content = new ArrayList<>();

    public NotifikacijeListaDto() {
    }

    public NotifikacijeListaDto(List<NotifikacijeDto> content) {
        this.content = content;
    }

    public List<NotifikacijeDto> getContent() {
        return content;
    }

    public void setContent(List<NotifikacijeDto> content) {
        this.content = content;
    }
}
