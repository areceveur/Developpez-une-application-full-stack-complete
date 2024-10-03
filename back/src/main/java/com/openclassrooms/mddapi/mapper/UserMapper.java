package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.requests.UserRequest;
import com.openclassrooms.mddapi.models.DBUser;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserRequest, DBUser> {
}
