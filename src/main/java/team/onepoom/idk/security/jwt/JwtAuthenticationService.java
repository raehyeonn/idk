package team.onepoom.idk.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final JwtUtils jwtUtils;
    private final TokenProvider tokenProvider;

    public Authentication authenticateWithAccessToken(String token, HttpServletRequest request) {
        if(!jwtUtils.isTokenExpired(token)) {
            return processToken(token, request);
        } else {
            throw new ExpiredJwtException(null, null, "유효하지 않은 토큰입니다.");
        }
    }

    public Authentication processToken(String token, HttpServletRequest httpServletRequest) {
        UserDetails userDetails = jwtUtils.extractUserDetailsFromToken(token);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        return authentication;
    }

    /**
     * 리프레시 토큰을 사용하여 새로운 액세스 토큰을 생성합니다.
     *
     * @param refreshToken 리프레시 토큰
     * @param request      HTTP 요청
     * @param response     HTTP 응답
     */
    public Authentication refreshAccessToken(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        UserDetails userDetails = jwtUtils.extractUserDetailsFromToken(refreshToken);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        String newAccessToken = tokenProvider.createAccessToken(authentication);

        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
