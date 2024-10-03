package com.openclassrooms.mddapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.dto.requests.ArticleRequest;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.mapper.ArticleMapperImpl;
import com.openclassrooms.mddapi.models.DBArticle;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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

    @WithMockUser(username = "user@test.com", roles = "USER")
    @Test
    public void createArticleTest() throws Exception {
        String currentUserEmail = "user@test.com";
        Long ownerId = 1L;

        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setTitre("Titre de l'article");
        articleRequest.setContenu("Contenu de l'article");
        articleRequest.setThemeId("Développement web");

        DBArticle createdArticle = new DBArticle();
        createdArticle.setId(1L);
        createdArticle.setTitre("Titre de l'article");
        createdArticle.setContenu("Contenu de l'article");
        createdArticle.setThemeId("Développement web");
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
                //.andExpect(jsonPath("$.titre").value("Titre de l'article"))
                //.andExpect(jsonPath("$.contenu").value("Contenu de l'article"));
    }

    @Test
    public void testArticleMapper() {
        ArticleMapper articleMapper = new ArticleMapperImpl();

        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setTitre("Test Title");
        articleRequest.setContenu("Test Content");
        articleRequest.setThemeId("Test Theme");

        DBArticle dbArticle = articleMapper.toEntity(articleRequest);

        assertEquals("Test Title", dbArticle.getTitre());
        assertEquals("Test Content", dbArticle.getContenu());
        assertEquals("Test Theme", dbArticle.getThemeId());
    }

}
