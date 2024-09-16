package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
