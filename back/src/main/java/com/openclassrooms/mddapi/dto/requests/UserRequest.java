package com.openclassrooms.mddapi.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@NoArgsConstructor
public class UserRequest {
    private Long id;
    private String email;
    private String username;
    private List<SubscriptionRequest> subscriptions;
    private List<ThemeRequest> themes;

    public UserRequest(Long id, String email, String username, List<SubscriptionRequest> subscriptions, List<ThemeRequest> themes) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.subscriptions = subscriptions;
        this.themes = themes;
    }
}
