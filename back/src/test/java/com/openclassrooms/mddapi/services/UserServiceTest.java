package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.Optional;


@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testFindUserByEmail() {
        DBUser user = new DBUser();
        user.setId(1L);
        user.setUsername("Test Test");
        user.setEmail("test@test.com");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        Long userId = userService.findUserByEmail("test@test.com");

        assertNotNull(userId);
        assertEquals(1L, userId);
    }

    @Test
    public void testSaveUser() {
        DBUser user = new DBUser();
        user.setUsername("UserTest");
        user.setEmail("test1@test.com");

        when(userRepository.save(user)).thenReturn(user);

        DBUser createdUser = userService.saveUser(user);
        assertNotNull(createdUser);
        assertEquals("UserTest", createdUser.getUsername());
        assertEquals("test1@test.com", createdUser.getEmail());
    }

    @Test
    public void testFindUserByUsernameById_UserFound() {
        DBUser user = new DBUser();
        user.setId(1L);
        user.setUsername("TestUser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        String username = userService.findUserByUsernameById(1L);

        assertNotNull(username);
        assertEquals("TestUser", username);
    }

    @Test
    public void testFindUserByUsernameById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.findUserByUsernameById(1L);
        });

        assertEquals("User not found", thrown.getMessage());
    }

    @Test
    public void testGetUserByEmail() {
        DBUser user = new DBUser();
        user.setId(1L);
        user.setUsername("Test User");
        user.setEmail("test@test.com");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        Optional<DBUser> foundUser = userService.getUserByEmail("test@test.com");

        assertTrue(foundUser.isPresent());
        assertEquals("Test User", foundUser.get().getUsername());
        assertEquals("test@test.com", foundUser.get().getEmail());
    }
}