package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.DBArticle;
import com.openclassrooms.mddapi.models.DBComments;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentService(ArticleRepository articleRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public DBComments addComment(Long articleId, Long userId, String content) throws Exception {
        DBArticle article = articleRepository.findById(articleId)
                .orElseThrow(() -> new Exception("Article non trouvé"));

        DBUser dbUser = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Utilisateur non trouvé"));

        DBComments comments = new DBComments();
        comments.setArticle(article);
        comments.setUser(dbUser);
        comments.setComment(content);

        return commentRepository.save(comments);
    }

    public List<DBComments> getCommentsByArticle(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public List<DBComments> getCommentsByUser(Long userId) {
        return commentRepository.findByUserId(userId);
    }
}
