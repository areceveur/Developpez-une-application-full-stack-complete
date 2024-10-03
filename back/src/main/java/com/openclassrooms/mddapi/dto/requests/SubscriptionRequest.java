package com.openclassrooms.mddapi.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@AllArgsConstructor
@Getter
@Setter
public class SubscriptionRequest {
    private Long themeId;
    private Long userId;
}
