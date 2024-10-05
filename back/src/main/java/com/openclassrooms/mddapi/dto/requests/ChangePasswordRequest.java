package com.openclassrooms.mddapi.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;

}
