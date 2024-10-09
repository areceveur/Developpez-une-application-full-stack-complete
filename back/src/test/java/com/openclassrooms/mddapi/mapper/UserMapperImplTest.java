package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.requests.SubscriptionRequest;
import com.openclassrooms.mddapi.dto.requests.ThemeRequest;
import com.openclassrooms.mddapi.dto.requests.UserRequest;
import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserMapperImplTest {
    @Qualifier("userMapper")
    @InjectMocks
    private UserMapperImpl userMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ArticleMapperImpl articleMapperImpl;

    private SubscriptionRequest subscriptions;

    private ThemeRequest themes;

    @InjectMocks
    private ThemeMapperImpl themeMapper;

    @InjectMocks
    private SubscriptionMapperImpl subscriptionMapper;
    @Autowired
    private ThemeRepository themeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToEntity() {
        SubscriptionRequest subscriptionRequest1 = new SubscriptionRequest(1L, 1L);
        SubscriptionRequest subscriptionRequest2 = new SubscriptionRequest(2L, 1L);
        List<SubscriptionRequest> subscriptions = new ArrayList<>();
        subscriptions.add(subscriptionRequest1);
        subscriptions.add(subscriptionRequest2);

        ThemeRequest themeRequest1 = new ThemeRequest(1L, "Développement web", "Description");
        ThemeRequest themeRequest2 = new ThemeRequest(2L, "Langages de programmation", "Description");
        List<ThemeRequest> themes = new ArrayList<>();
        themes.add(themeRequest1);
        themes.add(themeRequest2);

        UserRequest userRequest = new UserRequest(1L, "user@test.com", "UserTest", subscriptions, themes);

        DBUser dbUser = userMapper.toEntity(userRequest);
        assertNotNull(dbUser);
    }

    @Test
    public void testToDto() {
        DBUser dbUser = new DBUser();
        dbUser.setUsername("UserTest");
        dbUser.setEmail("user@test.com");

        UserRequest userRequest = userMapper.toDto(dbUser);
        assertNotNull(userRequest);
    }

    @Test
    public void testToEntity_NullDto() {
        DBUser dbUser = userMapper.toEntity((UserRequest) null);
        assertNull(dbUser);
    }

    @Test
    public void testToDto_NullEntity() {
        UserRequest userRequest = userMapper.toDto((DBUser) null);
        assertNull(userRequest);
    }

    @Test
    public void testToEntityList() {
        SubscriptionRequest subscriptionRequest1 = new SubscriptionRequest(1L, 1L);
        SubscriptionRequest subscriptionRequest2 = new SubscriptionRequest(2L, 1L);
        List<SubscriptionRequest> subscriptions = new ArrayList<>();
        subscriptions.add(subscriptionRequest1);
        subscriptions.add(subscriptionRequest2);

        ThemeRequest themeRequest1 = new ThemeRequest(1L, "Développement web", "Description");
        ThemeRequest themeRequest2 = new ThemeRequest(2L, "Langages de programmation", "Description");
        List<ThemeRequest> themes = new ArrayList<>();
        themes.add(themeRequest1);
        themes.add(themeRequest2);

        UserRequest userRequest1 = new UserRequest(1L, "user@test.com", "UserTest", subscriptions, themes);

        DBUser dbUser1 = userMapper.toEntity(userRequest1);
        assertNotNull(dbUser1);

        UserRequest userRequest2 = new UserRequest(2L, "user2@test.com", "UserTest2", subscriptions, themes);

        DBUser dbUser2 = userMapper.toEntity(userRequest2);
        assertNotNull(dbUser2);

        List<UserRequest> userRequests = new ArrayList<>();
        userRequests.add(userRequest1);
        userRequests.add(userRequest2);

        List<DBUser> dbUsers = userMapper.toEntityList(userRequests);

        assertNotNull(dbUsers);
    }

    @Test
    public void testToEntityList_NullDto() {
        List<DBUser> result = userMapper.toEntityList((List<UserRequest>) null);
        assertNull(result);
    }

    @Test
    public void testToDtoList() {
        DBUser dbUser1 = new DBUser();
        dbUser1.setUsername("UserTest");
        dbUser1.setEmail("user@test.com");

        UserRequest userRequest1 = userMapper.toDto(dbUser1);
        assertNotNull(userRequest1);

        DBUser dbUser2 = new DBUser();
        dbUser2.setUsername("UserTest2");
        dbUser2.setEmail("user2@test.com");

        UserRequest userRequest2 = userMapper.toDto(dbUser2);
        assertNotNull(userRequest2);

        List<DBUser> dbUsers = new ArrayList<>();
        dbUsers.add(dbUser1);
        dbUsers.add(dbUser2);

        List<UserRequest> userRequests = userMapper.toDtoList(dbUsers);
        assertNotNull(userRequests);
    }

    @Test
    public void testToDtoList_NullDto() {
        List<UserRequest> result = userMapper.toDtoList((List<DBUser>) null);
        assertNull(result);
    }

    @Test
    public void testToThemeEntity() {
        ThemeRequest themeRequest = new ThemeRequest(1L, "Développement web", "Description");
        DBThemes dbThemes = userMapper.toThemeEntity(themeRequest);
        assertNotNull(dbThemes);
    }

    @Test
    public void testToThemeEntity_NullDto() {
        DBThemes dbThemes = userMapper.toThemeEntity(null);
        assertNull(dbThemes);
    }

    @Test
    public void testToThemeDto() {
        DBThemes dbThemes = new DBThemes();
        dbThemes.setName("Développement web");
        dbThemes.setDescription("Description");

        ThemeRequest themeRequest = userMapper.toThemeDto(dbThemes);
        assertNotNull(themeRequest);
    }

    @Test
    public void testToThemeDto_NullEntity() {
        ThemeRequest themeRequest = userMapper.toThemeDto(null);
        assertNull(themeRequest);
    }

    @Test
    public void testToSubscriptionDto_NullEntity() {
        SubscriptionRequest subscriptionRequest = userMapper.toSubscriptionDto(null);
        assertNull(subscriptionRequest);
    }

    @Test
    public void testToSubscriptionDto() {
        DBThemes dbThemes = new DBThemes();
        dbThemes.setName("Développement web");
        dbThemes.setDescription("Description");

        SubscriptionRequest subscriptionRequest = userMapper.toSubscriptionDto(dbThemes);
        assertNotNull(subscriptionRequest);
    }

    @Test
    public void testToSubscriptionEntity_NullDto() {
        DBThemes dbThemes = userMapper.toSubscriptionEntity(null);
        assertNull(dbThemes);
    }

    @Test
    public void testSubscriptionRequestListToDBThemesList_NullList() {
        List<DBThemes> result = userMapper.subscriptionRequestListToDBThemesList(null);
        assertNull(result);
    }

    @Test
    public void testThemeRequestListToDBThemesList_NullList() {
        List<DBThemes> result = userMapper.themeRequestListToDBThemesList(null);
        assertNull(result);
    }

    @Test
    public void testDbThemesListToSubscriptionRequestList() {
        DBThemes dbThemes = new DBThemes();
        dbThemes.setId(1L);
        dbThemes.setName("Développement web");
        dbThemes.setDescription("Description");

        List<DBThemes> dbThemesList = new ArrayList<>();
        dbThemesList.add(dbThemes);

        List<SubscriptionRequest> subscriptionRequests = userMapper.dBThemesListToSubscriptionRequestList(dbThemesList);
        assertNotNull(subscriptionRequests);
        assertEquals(dbThemesList.size(), subscriptionRequests.size());
    }

    @Test
    public void testDbThemesListToThemeRequestList() {
        DBThemes dbThemes = new DBThemes();
        dbThemes.setId(1L);
        dbThemes.setName("Développement web");
        dbThemes.setDescription("Description");

        List<DBThemes> dbThemesList = new ArrayList<>();
        dbThemesList.add(dbThemes);

        List<ThemeRequest> themeRequests = userMapper.dBThemesListToThemeRequestList(dbThemesList);
        assertNotNull(themeRequests);
        assertEquals(dbThemesList.size(), themeRequests.size());
    }

}
