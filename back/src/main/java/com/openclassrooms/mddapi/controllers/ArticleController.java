package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.requests.ArticleRequest;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.models.DBArticle;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("api/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleMapper articleMapper;

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
    public ResponseEntity<ArticleRequest> createArticle(@RequestBody ArticleRequest articleRequest) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long ownerId = userService.findUserByEmail(currentUserEmail);
        String auteur = userService.findUserByUsernameById(ownerId);

        DBArticle dbArticle = articleMapper.toEntity(articleRequest);
        dbArticle = articleService.saveArticle(dbArticle, ownerId, auteur);

        ArticleRequest responseDto = articleMapper.toDto(dbArticle);

        return ResponseEntity.ok(responseDto);
    }
}
