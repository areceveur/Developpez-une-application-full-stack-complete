package com.openclassrooms.mddapi.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleRequest {
    private String titre;
    private String contenu;
    private Long owner_id;
    private String themeId;
}
