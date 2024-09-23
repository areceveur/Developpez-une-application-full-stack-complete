package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ArticleRequest;
import com.openclassrooms.mddapi.dto.ArticleResponse;
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
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody ArticleRequest articleRequest) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long ownerId = userService.findUserByEmail(currentUserEmail);

        DBArticle dbArticle = new DBArticle();
        dbArticle.setTitre(articleRequest.getTitre());
        dbArticle.setContenu(articleRequest.getContenu());
        dbArticle.setOwner_id(ownerId);
        dbArticle.setCreated_at(new Date());
        dbArticle.setThemeId(articleRequest.getThemeId());

        DBArticle createdArticle = articleService.saveArticle(dbArticle);

        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.setTitre(createdArticle.getTitre());
        articleResponse.setContenu(createdArticle.getContenu());
        articleResponse.setOwner_id(createdArticle.getOwner_id());

        String auteur = userService.findUserByUsernameById(createdArticle.getOwner_id());
        articleResponse.setAuteur(auteur);

        return ResponseEntity.ok(articleResponse);
    }
}
