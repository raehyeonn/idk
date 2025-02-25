package team.onepoom.idk.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.onepoom.idk.auth.dto.LoginRequest;
import team.onepoom.idk.auth.dto.LoginResponse;
import team.onepoom.idk.security.cookie.CookieService;
import team.onepoom.idk.security.jwt.TokenProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final CookieService cookieService;

    @Transactional
    public void login(LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        log.debug("Attempting to authenticate user: {}", loginRequest.getEmail());
        Authentication authentication = authenticateMember(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("Authentication successful for user: {}", loginRequest.getEmail());

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);
        setTokensInResponse(httpServletResponse, accessToken, refreshToken);
    }

    private Authentication authenticateMember(LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            return authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            log.error("Authentication failed for user: {}", loginRequest.getEmail());
            throw new RuntimeException("Invalid credentials");
        }
    }

    public void setTokensInResponse(HttpServletResponse httpServletResponse, String accessToken, String refreshToken) {
        httpServletResponse.setHeader("Authorization", "Bearer " + accessToken);
        cookieService.addRefreshTokenCookie(httpServletResponse, refreshToken);
    }

    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        // 클라이언트에서 보낸 refresh_token을 쿠키에서 추출
        String refreshToken = cookieService.extractTokenFromCookie(httpServletRequest, "refresh_token");

        // refresh_token이 존재하면, 이를 삭제
        if (refreshToken != null) {
            cookieService.removeRefreshTokenCookie(httpServletResponse);
        }

        // SecurityContext 초기화
        SecurityContextHolder.clearContext();
        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setHeader("Expires", "0");
        log.debug("User logged out successfully");


    }



}
