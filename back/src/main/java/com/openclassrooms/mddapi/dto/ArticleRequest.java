package com.openclassrooms.mddapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleRequest {
    private String titre;
    private String contenu;
    private int owner_id;
}
