package com.openclassrooms.mddapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.dto.requests.ArticleRequest;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.mapper.ArticleMapperImpl;
import com.openclassrooms.mddapi.models.DBArticle;
import com.openclassrooms.mddapi.models.DBComments;
import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private UserService userService;

    @MockBean
    private ArticleMapper articleMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @MockBean
    private ThemeService themeService;

    @BeforeEach
    public void setUp() {
    }

    private String createJwtToken(String username) {
        return "jwtToken";
    }

    @Test
    @WithMockUser(username = "user@test.com")
    public void findAllArticlesTest() throws Exception {
        List<DBArticle> articles = new ArrayList<>();
        DBArticle dbArticle1 = new DBArticle();
        dbArticle1.setTitre("Article 1");
        articles.add(dbArticle1);

        when(articleService.getAllArticles()).thenReturn(articles);

        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titre").value("Article 1"));

        verify(articleService, times(1)).getAllArticles();
    }

    @WithMockUser(username = "user@test.com")
    @Test
    public void createArticleTest() throws Exception {
        String currentUserEmail = "user@test.com";
        Long ownerId = 1L;

        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setTitre("Titre de l'article");
        articleRequest.setContenu("Contenu de l'article");
        articleRequest.setThemeId(1L);

        DBArticle createdArticle = new DBArticle();
        createdArticle.setId(1L);
        createdArticle.setTitre("Titre de l'article");
        createdArticle.setContenu("Contenu de l'article");
        createdArticle.setThemeId(1L);
        createdArticle.setOwner_id(ownerId);
        createdArticle.setCreated_at(new Date());
        createdArticle.setUpdated_at(new Date());

        String auteur = "User Test";

        when(userService.findUserByEmail(currentUserEmail)).thenReturn(ownerId);
        when(articleService.saveArticle(any(DBArticle.class), eq(ownerId), eq(auteur))).thenReturn(createdArticle);
        when(articleMapper.toDto(createdArticle)).thenReturn(articleRequest);

        mockMvc.perform(post("/api/articles/create")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(articleRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testArticleMapper() {
        ArticleMapper articleMapper = new ArticleMapperImpl();

        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setTitre("Test Title");
        articleRequest.setContenu("Test Content");
        articleRequest.setThemeId(1L);

        DBArticle dbArticle = articleMapper.toEntity(articleRequest);

        assertEquals("Test Title", dbArticle.getTitre());
        assertEquals("Test Content", dbArticle.getContenu());
        assertEquals(1L, dbArticle.getThemeId());
    }

    @WithMockUser(username = "user@test.com", roles = "USER")
    @Test
    public void testGetArticleById() throws Exception {
        DBArticle dbArticle = new DBArticle();
        dbArticle.setId(16L);
        dbArticle.setTitre("Article 1");
        dbArticle.setContenu("Contenu");
        dbArticle.setThemeId(1L);
        dbArticle.setCreated_at(new Date());
        dbArticle.setAuteur("User Test");

        DBThemes dbTheme = new DBThemes();
        dbTheme.setId(1L);
        dbTheme.setName("Thème Test");
        when(themeService.getThemeById(1L)).thenReturn(Optional.of(dbTheme));
        when(articleService.getArticleById(16L)).thenReturn(Optional.of(dbArticle));

        mockMvc.perform(get("/api/articles/{id}", 16L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titre").value("Article 1"));

        verify(articleService, times(1)).getArticleById(16L);
    }

    @WithMockUser(username = "user@test.com")
    @Test
    public void testGetArticleById_NotFound() throws Exception {
        when(articleService.getArticleById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/articles/{id}", 1L))
                .andExpect(status().isNotFound());
    }


    @WithMockUser(username = "user@test.com")
    @Test
    public void testAddComment() throws Exception {
        Long articleId = 1L;
        Long userId = 1L;
        String commentContent = "This is a test comment.";

        DBComments dbComments = new DBComments();
        dbComments.setComment(commentContent);

        when(userService.findUserByEmail("user@test.com")).thenReturn(userId);
        when(commentService.addComment(articleId, userId, commentContent)).thenReturn(dbComments);

        mockMvc.perform(post("/api/articles/{articleId}/comments", articleId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"" + commentContent + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value(commentContent));

        verify(commentService, times(1)).addComment(articleId, userId, commentContent);
    }

    @WithMockUser(username = "user@test.com")
    @Test
    public void testGetCommentsByArticle() throws Exception {
        Long articleId = 1L;

        List<DBComments> comments = new ArrayList<>();
        DBComments comment1 = new DBComments();
        comment1.setComment("Comment 1");
        comments.add(comment1);

        DBComments comment2 = new DBComments();
        comment2.setComment("Comment 2");
        comments.add(comment2);

        when(commentService.getCommentsByArticle(articleId)).thenReturn(comments);

        mockMvc.perform(get("/api/articles/{articleId}/comments", articleId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment").value("Comment 1"))
                .andExpect(jsonPath("$[1].comment").value("Comment 2"));

        verify(commentService, times(1)).getCommentsByArticle(articleId);
    }
}
