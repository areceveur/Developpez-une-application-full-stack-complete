package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ARTICLES")
public class DBArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    private String contenu;
    private int owner_id;
    private Date created_at;
    private Date updated_at;
}
