package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.requests.ThemeRequest;
import com.openclassrooms.mddapi.models.DBThemes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThemeMapper {
    DBThemes toEntity(ThemeRequest dto);
    ThemeRequest toDto(DBThemes entity);
}
