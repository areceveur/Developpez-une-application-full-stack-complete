package com.openclassrooms.mddapi.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {
    private String email;
    private String username;
    private String newEmail;
}
