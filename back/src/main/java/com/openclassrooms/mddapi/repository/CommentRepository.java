package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.DBComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<DBComments, Long> {
    List<DBComments> findByArticleId(Long articleId);
}
