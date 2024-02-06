package org.example.restClient.dto;

import java.util.ArrayList;
import java.util.List;

public class TerminTreningaListDto {
    private List<TerminTreningaDto> content = new ArrayList<>();

    public TerminTreningaListDto() {
    }

    public TerminTreningaListDto(List<TerminTreningaDto> content) {
        this.content = content;
    }

    public List<TerminTreningaDto> getContent() {
        return content;
    }

    public void setContent(List<TerminTreningaDto> content) {
        this.content = content;
    }
}
