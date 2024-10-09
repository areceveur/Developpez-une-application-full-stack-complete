package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.requests.SubscriptionRequest;
import com.openclassrooms.mddapi.models.DBSubscriptions;
import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
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
public class SubscriptionServiceTest {
    @Mock
    SubscriptionRepository subscriptionRepository;

    @InjectMocks
    SubscriptionService subscriptionService;

    @Mock
    private SubscriptionRequest subscriptionRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSubscriptionsByUserId() {

        DBUser dbUser = new DBUser();
        dbUser.setId(1L);

        DBThemes dbThemes1 = new DBThemes();
        dbThemes1.setId(1L);
        dbThemes1.setName("Développement web");

        DBThemes dbThemes2 = new DBThemes();
        dbThemes2.setId(2L);
        dbThemes2.setName("Langages de programmation");

        DBSubscriptions dbSubscription1 = new DBSubscriptions();
        dbSubscription1.setUser(dbUser);
        dbSubscription1.setTheme(dbThemes1);

        DBSubscriptions dbSubscription2 = new DBSubscriptions();
        dbSubscription2.setUser(dbUser);
        dbSubscription2.setTheme(dbThemes2);

        List<DBSubscriptions> dbSubscriptionsList = new ArrayList<>();
        dbSubscriptionsList.add(dbSubscription1);
        dbSubscriptionsList.add(dbSubscription2);

        when(subscriptionRepository.findByUser_Id(1L)).thenReturn(dbSubscriptionsList);

        List<SubscriptionRequest> result = subscriptionService.getSubscriptionsByUserId(1L);

        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).getThemeId());
        assertEquals(1L, result.get(0).getUserId());

        assertEquals(2L, result.get(1).getThemeId());
        assertEquals(1L, result.get(1).getUserId());
    }
}
