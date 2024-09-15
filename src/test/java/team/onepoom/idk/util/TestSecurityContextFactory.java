package team.onepoom.idk.util;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import team.onepoom.idk.domain.Provider;

public class TestSecurityContextFactory {

    public static RequestPostProcessor authenticatedProvider(Provider provider) {
        return request -> {
            SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken(provider, null, "ROLE_USER"));
            return request;
        };
    }

}
