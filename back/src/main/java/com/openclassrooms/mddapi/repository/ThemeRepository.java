package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.DBThemes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends CrudRepository<DBThemes, Long> {
}
