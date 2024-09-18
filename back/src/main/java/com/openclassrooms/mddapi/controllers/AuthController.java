package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.RegisterRequest;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.services.JWTService;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
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
    public ResponseEntity<TokenResponse> registerUser(@RequestBody RegisterRequest registerRequest) {

        DBUser dbUser = new DBUser();
        dbUser.setEmail(registerRequest.getEmail());
        dbUser.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        dbUser.setUsername(registerRequest.getUsername());
        dbUser.setUpdatedAt(new Date());

        userService.saveUser(dbUser);

        String token = jwtService.generateToken(new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword()));
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<DBUser> me(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            Jwt jwt = jwtDecoder.decode(token);
            String username = jwt.getSubject();
            Optional<DBUser> user = userService.getUserByEmail(username);
            return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
