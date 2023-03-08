package WebProject.withpet.common.config;

import WebProject.withpet.auth.application.JwtTokenProvider;
import WebProject.withpet.common.exception.UnauthorizedException;
import WebProject.withpet.common.util.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String header = request.getHeader(JwtTokenProvider.HEADER_STRING);

        if (header == null) {
            throw new UnauthorizedException();
        }
        JwtUtil.verify(JwtUtil.getToken(header), jwtTokenProvider.getSecretKey());

        return true;
    }

}