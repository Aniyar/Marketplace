package com.example.bakery.controller;

import com.example.bakery.model.Role;
import com.example.bakery.request.AuthenticationRequest;
import com.example.bakery.response.AuthenticationResponse;
import com.example.bakery.request.RegisterRequest;
import com.example.bakery.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping ("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request, Role.USER));
    }

    @PostMapping ("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)
    {
        return ResponseEntity.ok(authService.authenticate(request));

    }

}
