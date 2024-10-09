package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.requests.SubscriptionRequest;
import com.openclassrooms.mddapi.models.DBSubscriptions;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    DBSubscriptions toEntity(SubscriptionRequest dto);
    SubscriptionRequest toDto(DBSubscriptions entity);
}
