package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.requests.ThemeRequest;
import com.openclassrooms.mddapi.models.DBThemes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ThemeMapperImplTest {
    @Autowired
    private ThemeMapperImpl themeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void toEntityTest() {
        ThemeRequest themeRequest = new ThemeRequest(1L, "Développement web", "Description");
        DBThemes dbThemes = themeMapper.toEntity(themeRequest);
        assertNotNull(dbThemes);
    }

    @Test
    public void toDtoTest() {
        DBThemes dbThemes = new DBThemes();
        dbThemes.setName("Développement web");
        dbThemes.setDescription("Description");

        ThemeRequest themeRequest = themeMapper.toDto(dbThemes);
        assertNotNull(themeRequest);
    }

    @Test
    public void testToEntity_NullDto() {
        DBThemes dbThemes = themeMapper.toEntity(null);
        assertNull(dbThemes);
    }

    @Test
    public void testToDto_NullEntity() {
        ThemeRequest themeRequest = themeMapper.toDto(null);
        assertNull(themeRequest);
    }

}
