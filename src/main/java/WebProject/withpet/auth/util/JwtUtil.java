package WebProject.withpet.auth.util;

import WebProject.withpet.auth.application.JwtTokenProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtUtil {
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.cookie-valid-time}")
    private Long cookieValidTime;

    // TODO : ExpiredException 발생 시 refresh token 으로 넘어가도록 수정
    public static void verify(String token, String secretKey) throws Exception {
        JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
    }

    public static String getToken(String header) {
        return header.replace(JwtTokenProvider.TOKEN_PREFIX, "");
    }

    public ResponseCookie createCookie(String token) {
        return ResponseCookie.from(JwtTokenProvider.REFRESH_TOKEN_HEADER_STRING, token).path("/").sameSite("None")
                .secure(true).maxAge(Math.toIntExact(cookieValidTime)).httpOnly(true).build();
    }
}
