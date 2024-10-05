package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.requests.ArticleRequest;
import com.openclassrooms.mddapi.dto.requests.CommentRequest;
import com.openclassrooms.mddapi.dto.response.CommentResponse;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.models.DBArticle;
import com.openclassrooms.mddapi.models.DBComments;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
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
    @Autowired
    private final ArticleService articleService;
    @Autowired
    private final UserService userService;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CommentService commentService;

    @Autowired
    public ArticleController(ArticleService articleService, UserService userService, CommentService commentService) {
        this.articleService = articleService;
        this.userService = userService;
        this.commentService = commentService;
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
        Optional<DBArticle> dbArticle = this.articleService.getArticleById(id);

        if (dbArticle.isPresent()) {
            return ResponseEntity.ok(dbArticle.get());
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
