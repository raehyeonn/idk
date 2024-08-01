package team.onepoom.idk.common.advice;

import java.util.Optional;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import team.onepoom.idk.common.exception.UserForbiddenException;
import team.onepoom.idk.domain.Provider;
import team.onepoom.idk.domain.user.Role;

@Aspect
@Component
public class SuspendDeniedAdvice {

    @Before("@annotation(team.onepoom.idk.common.annotation.SuspendDenied)")
    public void suspendDenied() {
        Provider provider = getCurrentProvider();
        if(provider!=null && provider.roles().contains(Role.SUSPEND))
            throw new UserForbiddenException();
    }

    private Provider getCurrentProvider() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Provider)
            return (Provider) authentication.getPrincipal();
        return null;
    }
}
