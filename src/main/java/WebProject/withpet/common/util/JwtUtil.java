package WebProject.withpet.common.util;

import WebProject.withpet.common.auth.application.JwtTokenProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtUtil {
    private final JwtTokenProvider jwtTokenProvider;

    public static void verify(String token, String secretKey) throws Exception {
        JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
    }

    public static String getToken(String header) {
        return header.replace(JwtTokenProvider.TOKEN_PREFIX, "");
    }
}
