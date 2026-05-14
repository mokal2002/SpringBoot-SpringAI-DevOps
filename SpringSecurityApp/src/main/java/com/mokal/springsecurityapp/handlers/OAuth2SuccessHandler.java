package com.mokal.springsecurityapp.handlers;

import com.mokal.springsecurityapp.entities.User;
import com.mokal.springsecurityapp.services.JwtService;
import com.mokal.springsecurityapp.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User OAuthUser = (DefaultOAuth2User) token.getPrincipal();

        String email = OAuthUser.getAttribute("email");

        User user = userService.findUserByEmail(email)
                .orElseGet(() -> {

                    User newUser = User.builder()
                            .name(OAuthUser.getAttribute("name"))
                            .email(email)
                            .build();

                    return userService.save(newUser);
                });

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateAccessToken(user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        String frontEndUrl = "http://localhost:8080/home.html?token=" + accessToken;

        getRedirectStrategy().sendRedirect(request, response, frontEndUrl);

    }
}
