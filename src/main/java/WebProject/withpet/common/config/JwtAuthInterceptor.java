package WebProject.withpet.common.config;

import WebProject.withpet.common.auth.application.JwtTokenProvider;
import WebProject.withpet.common.exception.UnauthorizedException;
import WebProject.withpet.users.repository.UserRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String givenToken = request.getHeader(JwtTokenProvider.HEADER_STRING);

        if (givenToken == null) {
            throw new UnauthorizedException();
        }
        jwtTokenProvider.verifyToken(givenToken);

        return true;
    }
}
