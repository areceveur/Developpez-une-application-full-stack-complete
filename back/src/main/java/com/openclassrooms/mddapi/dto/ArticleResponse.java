package com.openclassrooms.mddapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleResponse {
    private String titre;
    private String contenu;
    private Long owner_id;
    private String auteur;
}
