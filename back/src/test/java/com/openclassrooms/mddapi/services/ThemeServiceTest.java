package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.requests.SubscriptionRequest;
import com.openclassrooms.mddapi.models.DBSubscriptions;
import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ThemeServiceTest {
    @Mock
    private ThemeRepository themeRepository;

    @InjectMocks
    private ThemeService themeService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

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

    @Test
    public void testSubscribe() {

        DBUser dbUser = new DBUser();
        dbUser.setId(1L);
        dbUser.setUsername("UserTest");

        DBThemes dbThemes = new DBThemes();
        dbThemes.setId(1L);
        dbThemes.setName("Développement web");

        when(userRepository.findById(1L)).thenReturn(Optional.of(dbUser));
        when(themeRepository.findById(1L)).thenReturn(Optional.of(dbThemes));

        themeService.subscribe(1L,1L);
        verify(subscriptionRepository).save(any(DBSubscriptions.class));
    }

    @Test
    public void testUnsubscribe() {
        themeService.unsubscribe(1L, 1L);
        verify(subscriptionRepository).deleteByUser_IdAndTheme_Id(1L, 1L);
    }

    @Test
    public void testGetThemesByIds() {
        DBThemes dbThemes1 = new DBThemes();
        dbThemes1.setId(1L);
        dbThemes1.setName("Développement web");

        DBThemes dbThemes2 = new DBThemes();
        dbThemes2.setId(2L);
        dbThemes2.setName("Langages de programmation");

        List<Long> themeIds = new ArrayList<>();
        themeIds.add(dbThemes1.getId());
        themeIds.add(dbThemes2.getId());

        List<DBThemes> themesList = new ArrayList<>();
        themesList.add(dbThemes1);
        themesList.add(dbThemes2);

        when(themeRepository.findAllById(themeIds)).thenReturn(themesList);
        Iterable<DBThemes> result = themeService.getThemesByIds(themeIds);
        assertEquals(themesList, result);
    }
}
