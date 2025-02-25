package team.onepoom.idk.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtUtils jwtUtils;

    private String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(
        Collectors.joining(","));
    }

    public String createAccessToken(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        long userId = customUserDetails.getId();

        return Jwts.builder()
        .setIssuer("idk")
        .setSubject(authentication.getName())
        .claim("id", userId)
        .claim("authorities", getAuthorities(authentication))
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtUtils.getAccessTokenExpirationTime()))
        .setId(UUID.randomUUID().toString())
        .signWith(jwtUtils.createSigningKey(), SignatureAlgorithm.HS256)

        .compact();
    }

    public String createRefreshToken(Authentication authentication) {
        return Jwts.builder()
        .setIssuer("idk")
        .setSubject(authentication.getName())
        .claim("authorities", getAuthorities(authentication))
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtUtils.getRefreshTokenExpirationTime()))
        .setId(UUID.randomUUID().toString())
        .signWith(jwtUtils.createSigningKey(), SignatureAlgorithm.HS256)
        .compact();
    }

}
