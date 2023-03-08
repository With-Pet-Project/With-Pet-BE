package WebProject.withpet.auth.application;

import WebProject.withpet.auth.PrincipalDetails;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.secret-key}")
    private String secretKey;
    public static final String HEADER_STRING = "Authorization";
    @Value("${jwt.valid-time}")
    private String tokenValidTime;
    @Value("${jwt.refresh-valid-time}")
    private Long refreshTokenValidTime;
    public static final String TOKEN_PREFIX = "Bearer ";

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;


    public String createToken(User user) {
        return JWT.create().withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(tokenValidTime)))
                .withClaim("id", user.getId()).withClaim("email", user.getEmail()).sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken(User user) {
        return JWT.create().withSubject(user.getEmail()).withExpiresAt(
                        new Date(System.currentTimeMillis() + Long.parseLong(String.valueOf(refreshTokenValidTime))))
                .withClaim("id", user.getId()).withClaim("email", user.getEmail()).sign(Algorithm.HMAC512(secretKey));
    }

    /*
		 Token에 담겨있는 정보를 이용해 Authentication 객체를 반환하는 메서드
	 */
    public Authentication getAuthentication(String JwtToken) {
        String token = JwtToken.replace(TOKEN_PREFIX, "");
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
}
