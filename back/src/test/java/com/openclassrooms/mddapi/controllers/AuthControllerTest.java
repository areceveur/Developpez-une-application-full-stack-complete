package com.openclassrooms.mddapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.dto.requests.ChangePasswordRequest;
import com.openclassrooms.mddapi.dto.requests.RegisterRequest;
import com.openclassrooms.mddapi.dto.requests.UpdateProfileRequest;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.services.JWTService;
import com.openclassrooms.mddapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Qualifier("jwtDecoder")
    @MockBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateUser() throws Exception {

        DBUser dbUser = new DBUser();
        dbUser.setEmail("user@test.com");
        dbUser.setPassword(bCryptPasswordEncoder.encode("password"));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user@test.com");

        when(userService.getUserByEmail("user@test.com")).thenReturn(Optional.of(dbUser));
        when(bCryptPasswordEncoder.matches("password", dbUser.getPassword())).thenReturn(true);
        String expectedToken = jwtService.generateToken(authentication, dbUser.getId());

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"user@test.com\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(expectedToken)));
    }

    @Test
    public void testAuthenticateUserUnauthorized() throws Exception {
        when(userService.getUserByEmail("user@test.com")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"user@test.com\", \"password\":\"password\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testRegisterUserEmailAlreadyExists() throws Exception {
        DBUser existingUser = new DBUser();
        existingUser.setEmail("user@test.com");
        when(userService.getUserByEmail("user@test.com")).thenReturn(Optional.of(existingUser));

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("user@test.com");
        registerRequest.setPassword("password");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"user@test.com\", \"password\":\"password\"}"))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email already exists"));
    }

    @Test
    public void testRegisterUser() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@user.com");
        registerRequest.setPassword("password");
        registerRequest.setUsername("UserTest");

        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("password");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registerRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@test.com")
    public void testMe() throws Exception {
        DBUser dbUser = new DBUser();
        dbUser.setEmail("user@test.com");
        dbUser.setUsername("UserTest");
        dbUser.setUpdatedAt(new Date());

        Jwt jwt = Mockito.mock(Jwt.class);
        when(jwt.getSubject()).thenReturn("user@test.com");
        when(jwtDecoder.decode(anyString())).thenReturn(jwt);

        when(userService.getUserByEmail("user@test.com")).thenReturn(Optional.of(dbUser));

        mockMvc.perform(get("/api/auth/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer validToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@test.com"))
                .andExpect(jsonPath("$.username").value("UserTest"));
    }

    @Test
    public void testMeNoAuthHeader() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "user@test.com")
    @Test
    public void testUpdateProfileSuccess() throws Exception {
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setCurrentEmail("user@test.com");
        updateProfileRequest.setUsername("NewUser");
        updateProfileRequest.setNewEmail("newemail@test.com");

        mockMvc.perform(put("/api/auth/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateProfileRequest)))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user@test.com")
    @Test
    public void testUpdateProfileBadRequest() throws Exception {
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setCurrentEmail("user@test.com");
        updateProfileRequest.setUsername("NewUser");
        updateProfileRequest.setNewEmail("newemail@test.com");

        doThrow(new JwtException("Invalid token"))
                .when(userService).updateUserProfile(Mockito.any(UpdateProfileRequest.class));

        mockMvc.perform(put("/api/auth/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateProfileRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erreur lors de la modificationInvalid token"));
    }

    @Test
    @WithMockUser(username = "user@test.com")
    public void testUpdatePasswordSuccess() throws Exception {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("oldPassword", "newPassword");

        DBUser dbUser = new DBUser();
        dbUser.setEmail("user@test.com");
        dbUser.setPassword(bCryptPasswordEncoder.encode("oldPassword"));

        Jwt jwt = Mockito.mock(Jwt.class);
        when(jwt.getSubject()).thenReturn("user@test.com");
        when(jwtDecoder.decode(anyString())).thenReturn(jwt);
        when(userService.getUserByEmail("user@test.com")).thenReturn(Optional.of(dbUser));
        when(bCryptPasswordEncoder.matches("oldPassword", dbUser.getPassword())).thenReturn(true);

        mockMvc.perform(post("/api/auth/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer validToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(changePasswordRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Mot de passe mis à jour"));
    }

    @Test
    @WithMockUser(username = "user@test.com")
    public void testUpdatePasswordCurrentPasswordIncorrect() throws Exception {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("wrongPassword", "newPassword");

        DBUser dbUser = new DBUser();
        dbUser.setEmail("user@test.com");
        dbUser.setPassword(bCryptPasswordEncoder.encode("oldPassword"));

        Jwt jwt = Mockito.mock(Jwt.class);
        when(jwt.getSubject()).thenReturn("user@test.com");
        when(jwtDecoder.decode(anyString())).thenReturn(jwt);
        when(userService.getUserByEmail("user@test.com")).thenReturn(Optional.of(dbUser));
        when(bCryptPasswordEncoder.matches("wrongPassword", dbUser.getPassword())).thenReturn(false);

        mockMvc.perform(post("/api/auth/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer validToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(changePasswordRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Mot de passe actuel incorrect"));
    }
}
