package team.onepoom.idk.security.config;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import team.onepoom.idk.member.domain.Role;
import team.onepoom.idk.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(auth -> auth

        .requestMatchers("/", "/api/auth/login").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/members").permitAll()
        .requestMatchers(HttpMethod.PATCH, "/api/members/**").hasAnyRole(Role.MEMBER.name(), Role.ADMIN.name())
        .requestMatchers(HttpMethod.DELETE, "/api/members").hasRole(Role.MEMBER.name())

        .requestMatchers(HttpMethod.GET, "/api/notices/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/notices/**").hasRole(Role.ADMIN.name())
        .requestMatchers(HttpMethod.PATCH, "/api/notices/**").hasRole(Role.ADMIN.name())
        .requestMatchers(HttpMethod.DELETE, "/api/notices/**").hasRole(Role.ADMIN.name())

        .requestMatchers(HttpMethod.GET, "/api/questions").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/questions/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/questions/me").hasRole(Role.MEMBER.name())
        .requestMatchers(HttpMethod.POST, "/api/questions/**").hasRole(Role.MEMBER.name())
        .requestMatchers(HttpMethod.PATCH, "/api/questions/**").hasRole(Role.MEMBER.name())
        .requestMatchers(HttpMethod.DELETE, "/api/questions/**").hasRole(Role.MEMBER.name())


        .requestMatchers("/api/answers/**").hasAnyRole(Role.MEMBER.name())

        .requestMatchers(HttpMethod.GET, "/api/reports/reasons").hasAnyRole(Role.MEMBER.name(), Role.ADMIN.name())
        .requestMatchers(HttpMethod.POST, "/api/reports/reasons").hasRole(Role.ADMIN.name())
        .requestMatchers(HttpMethod.DELETE, "/api/reports/reasons/**").hasRole(Role.ADMIN.name())

        .requestMatchers(HttpMethod.POST, "/api/reports/questions").hasRole(Role.MEMBER.name())
        .requestMatchers(HttpMethod.GET, "/api/reports/questions").hasRole(Role.ADMIN.name())
        .requestMatchers(HttpMethod.DELETE, "/api/reports/questions/**").hasRole(Role.ADMIN.name())
        .requestMatchers(HttpMethod.POST, "/api/reports/questions/**").hasRole(Role.ADMIN.name())

        .requestMatchers(HttpMethod.POST, "/api/reports/answers").hasRole(Role.MEMBER.name())
        .requestMatchers(HttpMethod.GET, "/api/reports/answers").hasRole(Role.ADMIN.name())
        .requestMatchers(HttpMethod.DELETE, "/api/reports/answers/**").hasRole(Role.ADMIN.name())
        .requestMatchers(HttpMethod.POST, "/api/reports/answers/**").hasRole(Role.ADMIN.name())

        .anyRequest()
        .authenticated());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-Requested-With", "Accept", "Origin"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization"));

        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
