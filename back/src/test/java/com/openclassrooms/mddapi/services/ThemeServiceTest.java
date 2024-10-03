package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ThemeServiceTest {
    @Mock
    private ThemeRepository themeRepository;

    @InjectMocks
    private ThemeService themeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllThemes() {
        DBThemes dbThemes1 = new DBThemes();
        dbThemes1.setName("Développement web");

        DBThemes dbThemes2 = new DBThemes();
        dbThemes2.setName("Langages de programmation");

        List<DBThemes> themesList = new ArrayList<>();
        themesList.add(dbThemes1);
        themesList.add(dbThemes2);

        when(themeRepository.findAll()).thenReturn(themesList);
        Iterable<DBThemes> result = themeService.getAllThemes();
        assertEquals(themesList, result);

    }
}
