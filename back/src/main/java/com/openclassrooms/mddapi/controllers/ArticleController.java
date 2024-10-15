package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.requests.ArticleRequest;
import com.openclassrooms.mddapi.dto.requests.CommentRequest;
import com.openclassrooms.mddapi.dto.response.CommentResponse;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.models.DBArticle;
import com.openclassrooms.mddapi.models.DBComments;
import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    private final ArticleMapper articleMapper;
    private final CommentService commentService;
    private final ThemeService themeService;

    @Autowired
    public ArticleController(ArticleService articleService, UserService userService, CommentService commentService, ArticleMapper articleMapper, ThemeService themeService) {
        this.articleService = articleService;
        this.userService = userService;
        this.commentService = commentService;
        this.articleMapper = articleMapper;
        this.themeService = themeService;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable("id") Long id) {
        Optional<DBArticle> dbArticleOpt = this.articleService.getArticleById(id);

        if (dbArticleOpt.isPresent()) {
            DBArticle dbArticle = dbArticleOpt.get();
            String themeName = "";

            if (dbArticle.getThemeId() != null) {
                Optional<DBThemes> dbThemesOpt = themeService.getThemeById(dbArticle.getThemeId());
                if (dbThemesOpt.isPresent()) {
                    themeName = dbThemesOpt.get().getName();
                }
            }

            ArticleRequest requestDto = new ArticleRequest();
            requestDto.setThemeName(themeName);
            requestDto.setTitre(dbArticle.getTitre());
            requestDto.setAuteur(dbArticle.getAuteur());
            requestDto.setContenu(dbArticle.getContenu());
            requestDto.setCreated_at(dbArticle.getCreated_at());

            return ResponseEntity.ok(requestDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("{articleId}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long articleId, @Valid @RequestBody CommentRequest commentRequest) throws Exception {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.findUserByEmail(currentUserEmail);

        DBComments saveComment = commentService.addComment(articleId, userId, commentRequest.getContent());
        return ResponseEntity.ok(saveComment);
    }

    @GetMapping("{articleId}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsByArticle(@PathVariable Long articleId) {
        List<DBComments> comments = commentService.getCommentsByArticle(articleId);
        List<CommentResponse> response = comments.stream()
                .map(CommentResponse::new)
                .toList();
        return ResponseEntity.ok(response);
    }
}
