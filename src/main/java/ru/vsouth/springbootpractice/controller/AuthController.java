package ru.vsouth.springbootpractice.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsouth.springbootpractice.dto.AuthRequest;
import ru.vsouth.springbootpractice.entity.UserInfo;
import ru.vsouth.springbootpractice.service.JwtService;
import ru.vsouth.springbootpractice.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/new")
    public Map<String, String> createUser(@RequestBody UserInfo userInfo) {
        System.out.println(userInfo.toString());
        userService.addUser(userInfo);
        String token = jwtService.generateToken(userInfo.getLogin());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/authenticate")
    public Map<String, String> authenticateUser(@RequestBody AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authRequest.getLogin(),
                        authRequest.getPassword());
        authenticationManager.authenticate(authInputToken);
        String token = jwtService.generateToken(authRequest.getLogin());
        return Map.of("jwt-token", token);
    }
}
