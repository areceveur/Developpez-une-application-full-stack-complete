package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.requests.*;
import com.openclassrooms.mddapi.dto.response.TokenResponse;
import com.openclassrooms.mddapi.mapper.UserMapperImpl;
import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.services.JWTService;
import com.openclassrooms.mddapi.services.SubscriptionService;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final UserService userService;
    private final JWTService jwtService;
    private final JwtDecoder jwtDecoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final SubscriptionService subscriptionService;
    private final ThemeService themeService;
    private final UserMapperImpl userMapperImpl;

    @Autowired
    public AuthController(UserService userService, JWTService jwtService, JwtDecoder jwtDecoder, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, SubscriptionService subscriptionService, ThemeService themeService, UserMapperImpl userMapperImpl) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.jwtDecoder = jwtDecoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.subscriptionService = subscriptionService;
        this.themeService = themeService;
        this.userMapperImpl = userMapperImpl;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Optional<DBUser> user = userService.getUserByEmail(loginRequest.getEmail());
        if (user.isPresent() && bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            String token = jwtService.generateToken(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), null), user.get().getId());
            DBUser foundUser = user.get();
            foundUser.setUpdatedAt(new Date());
            userService.saveUser(foundUser);
            return ResponseEntity.ok(new TokenResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        if (userService.getUserByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        DBUser dbUser = new DBUser();
        dbUser.setEmail(registerRequest.getEmail());
        dbUser.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        dbUser.setUsername(registerRequest.getUsername());
        dbUser.setUpdatedAt(new Date());

        userService.saveUser(dbUser);

        String token = jwtService.generateToken(new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), null), dbUser.getId());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<UserRequest> me(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring(7);
                Jwt jwt = jwtDecoder.decode(token);
                String username = jwt.getSubject();
                Optional<DBUser> user = userService.getUserByEmail(username);

                if (user.isPresent()) {
                    DBUser foundUser = user.get();
                    List<SubscriptionRequest> subscriptions = subscriptionService.getSubscriptionsByUserId(foundUser.getId());

                    List<Long> themeIds = subscriptions.stream()
                            .map(SubscriptionRequest::getThemeId)
                            .toList();

                    Iterable<DBThemes> themes = themeService.getThemesByIds(themeIds);
                    List<ThemeRequest> themeRequests = StreamSupport.stream(themes.spliterator(), false)
                            .map(theme -> new ThemeRequest(theme.getId(), theme.getName(), theme.getDescription()))
                            .toList();

                    UserRequest userRequest = new UserRequest(
                            foundUser.getId(),
                            foundUser.getEmail(),
                            foundUser.getUsername(),
                            subscriptions,
                            themeRequests
                    );
                    return ResponseEntity.ok(userRequest);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (JwtException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest request) {
        try {
            userService.updateUserProfile(request);
            return ResponseEntity.ok().build();
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la modification" + e.getMessage());
        }
    }

    @PostMapping("/me")
    public ResponseEntity<?> updatePassword(@RequestHeader(value = "Authorization", required = false) String authorizationHeader, @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring(7);
                Jwt jwt = jwtDecoder.decode(token);
                String username = jwt.getSubject();

                Optional<DBUser> userOptional = userService.getUserByEmail(username);
                if (userOptional.isPresent()) {
                    DBUser dbUser = userOptional.get();
                    if (bCryptPasswordEncoder.matches(changePasswordRequest.getCurrentPassword(), dbUser.getPassword())) {
                        dbUser.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));
                        userService.saveUser(dbUser);
                        return ResponseEntity.ok(Collections.singletonMap("message", "Mot de passe mis à jour"));
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mot de passe actuel incorrect");
                    }
                }
            } catch (JwtException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
