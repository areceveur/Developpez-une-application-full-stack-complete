package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.requests.ArticleRequest;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.models.DBArticle;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper articleMapper;

    private ArticleRequest articleRequest;

    public Iterable<DBArticle> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<DBArticle> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public DBArticle saveArticle(DBArticle dbArticle, Long ownerId, String auteur) {

        dbArticle.setOwner_id(ownerId);
        dbArticle.setAuteur(auteur);
        dbArticle.setCreated_at(new Date());
        dbArticle.setUpdated_at(new Date());

        return articleRepository.save(dbArticle);
    }
}
