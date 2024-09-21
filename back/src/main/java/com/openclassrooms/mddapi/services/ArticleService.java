package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.DBArticle;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public Iterable<DBArticle> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<DBArticle> getArticleById(int id) {
        return articleRepository.findById(id);
    }

    public DBArticle saveArticle(DBArticle article) {
        return articleRepository.save(article);
    }
}
