package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.requests.ArticleRequest;
import com.openclassrooms.mddapi.models.DBArticle;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ArticleMapperImplTest {
    @Qualifier("articleMapper")
    @InjectMocks
    private ArticleMapperImpl articleMapper;
    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void toEntityTest() {
        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setTitre("Titre de l'article");
        articleRequest.setContenu("Contenu de l'article");

        DBArticle article = articleMapper.toEntity(articleRequest);
        assertNotNull(article);
    }

    @Test
    public void toDtoTest() {
        DBArticle dbArticle = new DBArticle();
        dbArticle.setThemeId("Développement web");
        dbArticle.setContenu("Contenu de l'article");
        dbArticle.setTitre("Titre de l'article");

        ArticleRequest articleRequest = articleMapper.toDto(dbArticle);
        assertNotNull(articleRequest);
    }

    @Test
    public void testToEntity_NullDto() {
        DBArticle dbArticle = articleMapper.toEntity((ArticleRequest) null);
        assertNull(dbArticle);
    }

    @Test
    public void testToDto_NullEntity() {
        ArticleRequest articleRequest = articleMapper.toDto((DBArticle) null);
        assertNull(articleRequest);
    }
}
