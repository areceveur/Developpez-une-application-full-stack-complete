package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.requests.ThemeRequest;
import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {
    @Autowired
    private ThemeService themeService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<ThemeRequest>> getAllThemes() {
        Iterable<DBThemes> themes = themeService.getAllThemes();

        List<ThemeRequest> themeRequests = StreamSupport.stream(themes.spliterator(), false)
                .map(theme -> new ThemeRequest(theme.getId(), theme.getName(), theme.getDescription()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(themeRequests);
    }

    @PostMapping("/subscribe/{themeId}")
    public ResponseEntity<Void> subscribe(@PathVariable Long themeId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.findUserByEmail(currentUserEmail);
        themeService.subscribe(userId, themeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unsubscribe/{themeId}")
    public ResponseEntity<Void> unsubscribe(@PathVariable Long themeId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.findUserByEmail(currentUserEmail);

        themeService.unsubscribe(userId, themeId);
        return ResponseEntity.ok().build();
    }
}
