package com.mokal.springsecurityapp.controllers;

import com.mokal.springsecurityapp.dto.LoginDto;
import com.mokal.springsecurityapp.dto.SignUpDto;
import com.mokal.springsecurityapp.dto.UserDto;
import com.mokal.springsecurityapp.services.AuthService;
import com.mokal.springsecurityapp.services.JwtService;
import com.mokal.springsecurityapp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
     private  final UserService userService;
     private final AuthService authService;

     @PostMapping("/signup")
     public ResponseEntity<UserDto> signup(@RequestBody SignUpDto signUpDto) {
         UserDto userDto = userService.signup(signUpDto);
         return ResponseEntity.ok(userDto);
     }

     @PostMapping("/login")
     public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
         String token = authService.login(loginDto);
         Cookie cookie = new Cookie("token", token);
         cookie.setHttpOnly(true);
         response.addCookie(cookie);
         return ResponseEntity.ok(token);
     }
}
