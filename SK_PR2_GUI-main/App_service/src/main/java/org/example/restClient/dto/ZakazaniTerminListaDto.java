package org.example.restClient.dto;

import java.util.ArrayList;
import java.util.List;

public class ZakazaniTerminListaDto {
    private List<ZakazaniTerminDTO> content = new ArrayList<>();

    public ZakazaniTerminListaDto() {
    }

    public ZakazaniTerminListaDto(List<ZakazaniTerminDTO> content) {
        this.content = content;
    }

    public List<ZakazaniTerminDTO> getContent() {
        return content;
    }

    public void setContent(List<ZakazaniTerminDTO> content) {
        this.content = content;
    }
}

