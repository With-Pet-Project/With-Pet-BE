package WebProject.withpet.auth.util;

import static WebProject.withpet.auth.application.JwtTokenProvider.EXPIRE_DATE_STRING;

import WebProject.withpet.auth.application.JwtTokenProvider;
import WebProject.withpet.users.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtUtil {
    public static final String COOKIE_NAME = "Set-Cookie";
    public static final long REISSUE_STANDARD_DAY = 7;
    @Value("${jwt.secret-key}")
    private String tokenSecretKey;
    @Value("${jwt.cookie-valid-time}")
    private Long cookieValidTime;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public static void verify(String token, String secretKey) {
        JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
    }

    public static String getToken(String header) {
        return header.replace(JwtTokenProvider.TOKEN_PREFIX, "");
    }

    public ResponseCookie createCookie(String token) {
        return ResponseCookie.from(JwtTokenProvider.REFRESH_TOKEN_HEADER_STRING, token).path("/").sameSite("None")
                .secure(true).maxAge(Math.toIntExact(cookieValidTime)).httpOnly(true).build();
    }

    public String reissueAccessToken(String token, Long userId) {
        return jwtTokenProvider.createAccessToken(userRepository.findById(getUserId(token)).get());
    }

    public String reissueRefreshToken(Long userId) {
        return jwtTokenProvider.createRefreshToken(userRepository.findById(userId).get());
    }

    public boolean isTokenAboutToExpire(String token, Date requestedAt) {
        return (getExpireDate(token).getTime() - requestedAt.getTime()) / (24 * 60 * 60) <= REISSUE_STANDARD_DAY;
    }

    public Date getExpireDate(String token) {
        return JWT.require(Algorithm.HMAC512(tokenSecretKey)).build().verify(token).getClaim(EXPIRE_DATE_STRING)
                .asDate();
    }

    public Long getUserId(String token) {
        return JWT.require(Algorithm.HMAC512(tokenSecretKey)).build().verify(token).getClaim("id").asLong();
    }
}
