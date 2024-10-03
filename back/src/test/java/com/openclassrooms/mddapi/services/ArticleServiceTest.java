package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.DBArticle;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ArticleServiceTest {
    @MockBean
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Test
    public void testGetAllArticle() {
        DBArticle article1 = new DBArticle();
        article1.setId(1L);
        article1.setTitre("Premier titre");
        article1.setContenu("Premier contenu");
        article1.setCreated_at(new Date());

        DBArticle article2 = new DBArticle();
        article2.setId(2L);
        article2.setTitre("Deuxième titre");
        article2.setContenu("Deuxième contenu");
        article2.setCreated_at(new Date());

        Long owner_id = 1L;
        String auteur = "User Test";

        when(articleRepository.save(article1)).thenReturn(article1);
        when(articleRepository.save(article2)).thenReturn(article2);

        List<DBArticle> expectedArticles = Arrays.asList(article1, article2);
        when(articleRepository.findAll()).thenReturn(expectedArticles);

        DBArticle createdArticle1 = articleService.saveArticle(article1, owner_id, auteur);
        DBArticle createdArticle2 = articleService.saveArticle(article2, owner_id, auteur);

        assertNotNull(createdArticle1.getId());
        assertNotNull(createdArticle2.getId());

        Iterable<DBArticle> result = articleService.getAllArticles();
        assertNotNull(result);
    }

    @Test
    public void testGetArticleById() {
        DBArticle article = new DBArticle();
        article.setId(1L);
        article.setTitre("Titre de l'article");
        article.setContenu("Article");
        article.setCreated_at(new Date());

        Long owner_id = 1L;
        String auteur = "User Test";

        when(articleRepository.save(article)).thenReturn(article);
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        DBArticle createdArticle = articleService.saveArticle(article, owner_id, auteur);
        assertNotNull(createdArticle);
        assertNotNull(createdArticle.getId());

        Optional<DBArticle> foundArticle = articleService.getArticleById(1L);
        assertNotNull(foundArticle.isPresent());
        assertEquals("Titre de l'article", foundArticle.get().getTitre());

        Optional<DBArticle> nonExistentSession = articleService.getArticleById(-1L);
        assertFalse(nonExistentSession.isPresent());
    }
}
