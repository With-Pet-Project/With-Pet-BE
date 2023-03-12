package WebProject.withpet.auth.filter;

import WebProject.withpet.auth.application.JwtTokenProvider;
import WebProject.withpet.auth.util.JwtUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
    }

    @ResponseBody
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(JwtTokenProvider.ACCESS_TOKEN_HEADER_STRING);
        if (header == null || !header.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = null;
        String token = JwtUtil.getToken(header);

        // TODO : expired Exception catch 해서 refresh token 으로 넘기기
        try {
            JwtUtil.verify(token, tokenProvider.getSecretKey());
        } catch (TokenExpiredException e) {
            setErrorResponse(response, HttpStatus.BAD_REQUEST, "유효기간이 만료된 토큰입니다.");
            return;
        } catch (Exception e) {
            setErrorResponse(response, HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다.");
            return;
        }

        authentication = tokenProvider.getAuthentication(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(message);
    }
}