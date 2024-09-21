package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ArticleRequest;
import com.openclassrooms.mddapi.models.DBArticle;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Date;

@RestController
@RequestMapping("api/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<Iterable<DBArticle>> getAllArticles() {
        Iterable<DBArticle> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<DBArticle> createArticle(@RequestBody ArticleRequest articleRequest) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        int ownerId = userService.findUserByEmail(currentUserEmail);

        DBArticle dbArticle = new DBArticle();
        dbArticle.setTitre(articleRequest.getTitre());
        dbArticle.setContenu(articleRequest.getContenu());
        dbArticle.setOwner_id(ownerId);
        dbArticle.setCreated_at(new Date());

        DBArticle createdArticle = articleService.saveArticle(dbArticle);
        return ResponseEntity.ok(createdArticle);
    }
}
