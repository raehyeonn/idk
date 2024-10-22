package team.onepoom.idk.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import team.onepoom.idk.domain.user.User;

class ProviderBasicAuthenticationFilter extends BasicAuthenticationFilter {
    public ProviderBasicAuthenticationFilter(
        AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, Authentication authResult) {
        if (authResult.getPrincipal() instanceof User u) {
            PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(
                u.toProvider(), null, u.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }
}
