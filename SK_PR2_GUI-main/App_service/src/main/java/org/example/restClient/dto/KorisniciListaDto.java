package org.example.restClient.dto;

import java.util.ArrayList;
import java.util.List;

public class KorisniciListaDto {
    private List<KorisniciDto> content = new ArrayList<>();

    public KorisniciListaDto() {
    }

    public KorisniciListaDto(List<KorisniciDto> content) {
        this.content = content;
    }

    public List<KorisniciDto> getContent() {
        return content;
    }

    public void setContent(List<KorisniciDto> content) {
        this.content = content;
    }
}
