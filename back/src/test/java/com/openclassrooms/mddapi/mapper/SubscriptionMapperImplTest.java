package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.requests.SubscriptionRequest;
import com.openclassrooms.mddapi.models.DBSubscriptions;
import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.models.DBUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class SubscriptionMapperImplTest {
    @Autowired
    SubscriptionMapperImpl subscriptionMapper;
    @Autowired
    private ThemeMapperImpl themeMapperImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void toEntityTest() {
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(1L, 1L);
        DBSubscriptions dbSubscriptions = subscriptionMapper.toEntity(subscriptionRequest);
        assertNotNull(dbSubscriptions);
    }

    @Test
    public void toDtoTest() {
        DBThemes theme = new DBThemes();
        theme.setId(1L);

        DBUser user = new DBUser();
        user.setId(2L);

        DBSubscriptions dbSubscriptions = new DBSubscriptions();
        dbSubscriptions.setTheme(theme);
        dbSubscriptions.setUser(user);
        SubscriptionRequest subscriptionRequest = subscriptionMapper.toDto(dbSubscriptions);
        assertNotNull(subscriptionRequest);
    }

    @Test
    public void testToEntity_NullDto() {
        DBSubscriptions dbSubscriptions = subscriptionMapper.toEntity(null);
        assertNull(dbSubscriptions);
    }

    @Test
    public void testToDto_NullEntity() {
        SubscriptionRequest subscriptionRequest = subscriptionMapper.toDto(null);
        assertNull(subscriptionRequest);
    }
}
