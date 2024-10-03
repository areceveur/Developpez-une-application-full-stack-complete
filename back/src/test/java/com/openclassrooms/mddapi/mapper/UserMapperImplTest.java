package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.requests.SubscriptionRequest;
import com.openclassrooms.mddapi.dto.requests.UserRequest;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserMapperImplTest {
    @Qualifier("userMapper")
    @InjectMocks
    private UserMapperImpl userMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleMapperImpl articleMapperImpl;

    private SubscriptionRequest subscriptions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void toEntityTest() {
        UserRequest userRequest = new UserRequest(1L, "user@test.com", "UserTest",,);

        DBUser dbUser = userMapper.toEntity(userRequest);
        assertNotNull(dbUser);
    }

    @Test
    public void toDtoTest() {
        DBUser dbUser = new DBUser();
        dbUser.setUsername("UserTest");
        dbUser.setEmail("user@test.com");

        UserRequest userRequest = userMapper.toDto(dbUser);
        assertNotNull(userRequest);
    }

    @Test
    public void testToEntity_NUllDto() {
        DBUser dbUser = userMapper.toEntity((UserRequest) null);
        assertNull(dbUser);
    }

    @Test
    public void testToDto_NullEntity() {
        UserRequest userRequest = userMapper.toDto((DBUser) null);
        assertNull(userRequest);
    }

    @Test
    public void toEntityListTest() {
        UserRequest userRequest1 = new UserRequest("user@test.com", "UserTest");

        DBUser dbUser1 = userMapper.toEntity(userRequest1);
        assertNotNull(dbUser1);

        UserRequest userRequest2 = new UserRequest("user2@test.com", "UserTest2");

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
    public void toDtoListTest() {
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
}
