package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.DBArticle;
import com.openclassrooms.mddapi.models.DBComments;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddComment() throws Exception {
        DBArticle dbArticle = new DBArticle();
        dbArticle.setId(16L);
        dbArticle.setTitre("Titre");
        dbArticle.setContenu("Contenu");

        DBUser dbUser = new DBUser();
        dbUser.setUsername("UserTest");
        dbUser.setId(1L);

        DBComments dbComments = new DBComments();
        dbComments.setArticle(dbArticle);
        dbComments.setUser(dbUser);
        dbComments.setComment("Comment");

        when(articleRepository.findById(16L)).thenReturn(Optional.of(dbArticle));
        when(userRepository.findById(1L)).thenReturn(Optional.of(dbUser));
        when(commentRepository.save(any(DBComments.class))).thenReturn(dbComments);

        DBComments createdComment = commentService.addComment(16L, 1L, "Comment");

        assertNotNull(createdComment);
        assertEquals("Comment", createdComment.getComment());
    }

    @Test
    public void testAddCommentAndGetCommentsByArticle() throws Exception {
        DBArticle article = new DBArticle();
        article.setId(16L);

        when(articleRepository.findById(16L)).thenReturn(Optional.of(article));

        DBComments comment = new DBComments();
        comment.setComment("This is a test comment.");
        comment.setArticle(article);

        when(commentRepository.save(any(DBComments.class))).thenReturn(comment);

        DBComments createdComment = commentService.addComment(16L, 1L, "This is a test comment.");

        assertNotNull(createdComment);
        assertEquals("This is a test comment.", createdComment.getComment());

        List<DBComments> comments = new ArrayList<>();
        comments.add(createdComment);

        when(commentRepository.findByArticleId(16L)).thenReturn(comments);

        List<DBComments> result = commentService.getCommentsByArticle(16L);
        assertEquals(1, result.size());
        assertEquals("This is a test comment.", result.get(0).getComment());
    }




}
