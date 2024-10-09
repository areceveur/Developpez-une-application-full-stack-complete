package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "USERS")
public class DBUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private Date createdAt;
    private Date updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "SUBSCRIPTIONS",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private List<DBThemes> subscriptions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "SUBSCRIPTIONS",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private List<DBThemes> themes;
}
