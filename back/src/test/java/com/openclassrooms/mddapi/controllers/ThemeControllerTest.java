package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ThemeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThemeService themeService;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "user@test.com")
    public void testGetAllThemes() throws Exception {
        DBThemes theme1 = new DBThemes();
        theme1.setId(1L);
        theme1.setName("Développement web");
        DBThemes theme2 = new DBThemes();
        theme2.setId(2L);
        theme2.setName("Langage de programmation");

        when(themeService.getAllThemes()).thenReturn(Arrays.asList(theme1, theme2));

        mockMvc.perform(get("/api/themes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Développement web"))
                .andExpect(jsonPath("$[1].name").value("Langage de programmation"));
    }

    @Test
    @WithMockUser(username = "user@test.com")
    public void testSubscribe() throws Exception {
        when(userService.findUserByEmail("user@test.com")).thenReturn(1L);

        mockMvc.perform(post("/api/themes/subscribe/{themeId}", 1L))
                .andExpect(status().isOk());

        //verify(userService, times(1)).findUserByEmail("user@test.com");
        //verify(themeService, times(1)).subscribe(1L, 1L);
    }

    @Test
    @WithMockUser(username = "user@test.com")
    public void testUnsubscribe() throws Exception {
        when(userService.findUserByEmail("user@test.com")).thenReturn(1L);

        mockMvc.perform(delete("/api/themes/unsubscribe/{themeId}", 1L))
                .andExpect(status().isOk());

        //verify(userService, times(1)).findUserByEmail("user@test.com");
        //verify(themeService, times(1)).unsubscribe(1L, 1L);
    }
}
