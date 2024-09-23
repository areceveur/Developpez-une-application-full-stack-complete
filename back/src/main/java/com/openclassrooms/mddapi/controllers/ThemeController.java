package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {
    @Autowired
    private ThemeService themeService;

    @GetMapping
    public ResponseEntity<Iterable<DBThemes>> getAllThemes() {
        return ResponseEntity.ok(themeService.getAllThemes());
    }
}
