package WebProject.withpet.auth.application;

import WebProject.withpet.auth.PrincipalDetails;
import WebProject.withpet.auth.service.RefreshTokenService;
import WebProject.withpet.common.exception.InvalidRefreshTokenException;
import WebProject.withpet.common.exception.UserNotFoundException;
import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.secret-key}")
    private String secretKey;
    public static final String ACCESS_TOKEN_HEADER_STRING = "Authorization";
    public static final String REFRESH_TOKEN_HEADER_STRING = "RefreshToken";
    @Value("${jwt.valid-time}")
    private long TOKEN_VALID_TIME;
    @Value("${jwt.refresh-valid-time}")
    private long REFRESH_TOKEN_VALID_TIME;
    public static final String TOKEN_PREFIX = "Bearer ";
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;


    public String createToken(User user) {
        return JWT.create().withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALID_TIME)).withClaim("id", user.getId())
                .withClaim("email", user.getEmail()).sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken(User user) {
        return JWT.create().withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_TIME))
                .withClaim("id", user.getId()).withClaim("email", user.getEmail()).sign(Algorithm.HMAC512(secretKey));
    }

    /*
		 Token??? ???????????? ????????? ????????? Authentication ????????? ???????????? ?????????
	 */
    public Authentication getAuthentication(String jwtToken) {
        String token = jwtToken.replace(TOKEN_PREFIX, "");
        String email = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token).getClaim("email").asString();

        if (email != null) {
            Optional<User> userEntity = userRepository.findByEmail(email);
            try {
                User user = userEntity.get();
                PrincipalDetails principalDetails = new PrincipalDetails(user);
                return new UsernamePasswordAuthenticationToken(principalDetails, null,
                        principalDetails.getAuthorities());
            } catch (Exception e) {
                throw new UserNotFoundException();
            }
        }
        return null;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getNewAccessToken(String token) throws Exception {
        Long userId = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token).getClaim("id").asLong();
        if (refreshTokenService.isValidToken(token, userId)) {
            return createToken(userRepository.findById(userId).get());
        }
        throw new InvalidRefreshTokenException();
    }
}
