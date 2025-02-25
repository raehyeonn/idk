package team.onepoom.idk.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Getter
    @Value("${jwt.access-token.expiration-time}")
    private Long accessTokenExpirationTime;

    @Getter
    @Value("${jwt.refresh-token.expiration-time}")
    private Long refreshTokenExpirationTime;

    // JWT 서명을 생성할 때 사용되는 비밀 키를 생성합니다.
    public Key createSigningKey() {
        byte[] decodedKeyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(decodedKeyBytes);
    }

    // 요청 헤더에서 토큰을 추출합니다.
    public String extractTokenFromRequestHeader(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }

    // 응답 헤더에서 토큰을 추출합니다.
    public String extractTokenFromResponseHeader(HttpServletResponse httpServletResponse) {
        String authorizationHeader = httpServletResponse.getHeader("Authorization");

        if(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }

    // 토큰에서 클레임을 추출합니다.
    public Claims extractClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(createSigningKey()).build().parseClaimsJws(token).getBody();
    }

    // 토큰에서 사용자 정보를 추출합니다.
    public UserDetails extractUserDetailsFromToken(String token) {
        String Username = extractClaimsFromToken(token).getSubject();

        return userDetailsService.loadUserByUsername(Username);
    }

    // 토큰의 만료 여부를 확인합니다.
    // 현재 시간이 토큰의 만료 시간 이후라면 true(=토큰 만료), 아니라면 false(=토큰 유효)를 반환합니다.
    public boolean isTokenExpired(String token) {
        Date currentDate = new Date();
        Date expirationDate = extractClaimsFromToken(token).getExpiration();

        return currentDate.after(expirationDate);
    }

}
