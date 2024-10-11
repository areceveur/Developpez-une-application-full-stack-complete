package com.openclassrooms.mddapi.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ArticleRequest {
    private String titre;
    private String contenu;
    private Long owner_id;
    private Long themeId;
    private String themeName;
    private Date created_at;
    private String auteur;
}
