package com.openclassrooms.mddapi.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThemeRequest {
    private Long id;
    private String name;
    private String description;

    public ThemeRequest(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
