package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    public Iterable<DBThemes> getAllThemes() {
        return themeRepository.findAll();
    }
}
