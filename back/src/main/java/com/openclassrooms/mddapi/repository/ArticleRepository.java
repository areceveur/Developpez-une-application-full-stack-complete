package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.DBArticle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<DBArticle, Long> {
}
