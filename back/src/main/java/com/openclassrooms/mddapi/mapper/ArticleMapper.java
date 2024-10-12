package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.requests.ArticleRequest;
import com.openclassrooms.mddapi.models.DBArticle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    @Mapping(source = "themeId", target = "themeId")
    @Mapping(source = "auteur", target = "auteur")
    @Mapping(source = "titre", target = "titre")
    @Mapping(source = "contenu", target = "contenu")
    @Mapping(source = "owner_id", target = "owner_id")
    @Mapping(source = "created_at", target = "created_at")
    DBArticle toEntity(ArticleRequest dto);

    @Mapping(source = "themeId", target = "themeId")
    @Mapping(source = "auteur", target = "auteur")
    @Mapping(source = "titre", target = "titre")
    @Mapping(source = "contenu", target = "contenu")
    @Mapping(source = "owner_id", target = "owner_id")
    @Mapping(source = "created_at", target = "created_at")
    ArticleRequest toDto(DBArticle entity);
}
