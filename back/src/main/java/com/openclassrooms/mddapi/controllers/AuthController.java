package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.services.JWTService;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JWTService jwtService;
    private final JwtDecoder jwtDecoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthController(UserService userService, JWTService jwtService, JwtDecoder jwtDecoder, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.jwtDecoder = jwtDecoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticateUser(
            @RequestParam String email,
            @RequestParam String password) {

        Optional<DBUser> user = userService.getUserByEmail(email);
        if (user.isPresent() && bCryptPasswordEncoder.matches(password, user.get().getPassword())) {
            String token = jwtService.generateToken(new UsernamePasswordAuthenticationToken(email, password));
            DBUser foundUser = user.get();
            foundUser.setUpdatedAt(new Date());
            userService.saveUser(foundUser);
            return ResponseEntity.ok(new TokenResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> registerUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String username) {

        DBUser dbUser = new DBUser();
        dbUser.setEmail(email);
        dbUser.setPassword(bCryptPasswordEncoder.encode(password));
        dbUser.setUsername(username);
        dbUser.setUpdatedAt(new Date());

        userService.saveUser(dbUser);

        String token = jwtService.generateToken(new UsernamePasswordAuthenticationToken(email, password));
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
