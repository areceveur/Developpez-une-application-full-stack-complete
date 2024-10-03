package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.requests.ArticleRequest;
import com.openclassrooms.mddapi.models.DBArticle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    @Mapping(source = "themeId", target = "themeId")
    DBArticle toEntity(ArticleRequest dto);

    @Mapping(source = "themeId", target = "themeId")
    ArticleRequest toDto(DBArticle entity);
}
