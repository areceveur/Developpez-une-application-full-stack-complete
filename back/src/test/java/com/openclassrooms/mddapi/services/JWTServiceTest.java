package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.DBUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class JWTServiceTest {
    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JWTService jwtService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void testGenerateToken() {
        DBUser dbUser = new DBUser();
        dbUser.setId(1L);
        dbUser.setEmail("user@test.com");
        dbUser.setPassword(bCryptPasswordEncoder.encode("password"));

        when(authentication.getName()).thenReturn("test@example.com");

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("false-jwt-token");

        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        String token = jwtService.generateToken(authentication, dbUser.getId());

        assertEquals("false-jwt-token", token);

    }

}
