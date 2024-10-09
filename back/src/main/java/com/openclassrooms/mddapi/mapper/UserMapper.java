package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.requests.SubscriptionRequest;
import com.openclassrooms.mddapi.dto.requests.ThemeRequest;
import com.openclassrooms.mddapi.dto.requests.UserRequest;
import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.models.DBUser;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserRequest, DBUser> {

    DBUser toEntity(UserRequest dto);
    UserRequest toDto(DBUser entity);

    List<DBUser> toEntityList(List<UserRequest> dtoList);
    List<UserRequest> toDtoList(List<DBUser> entityList);

    DBThemes toThemeEntity(ThemeRequest dto);
    ThemeRequest toThemeDto(DBThemes entity);

    DBThemes toSubscriptionEntity(SubscriptionRequest dto);
    SubscriptionRequest toSubscriptionDto(DBThemes entity);
}
