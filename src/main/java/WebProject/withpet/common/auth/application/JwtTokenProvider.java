package WebProject.withpet.common.auth.application;

import WebProject.withpet.common.auth.PrincipalDetails;
import WebProject.withpet.common.exception.UserNotFoundException;
import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private String secretKey;
    public static final String HEADER_STRING = "Authorization";
    private long tokenValidTime;
    public static final String TOKEN_PREFIX = "Bearer ";
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey,
                            @Value("${jwt.valid-time}") long tokenValidTime) {
        this.secretKey = secretKey;
        this.tokenValidTime = tokenValidTime;
    }

    public String createToken(User user) {
        String jwtToken = JWT.create().withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenValidTime)).withClaim("id", user.getId())
                .withClaim("nickname", user.getNickName()).withClaim("email", user.getEmail())
                .sign(Algorithm.HMAC512(secretKey));
        return jwtToken;
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

    public void verifyToken(String givenToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secretKey)).build();
        String[] givenTokens = givenToken.split(TOKEN_PREFIX);
        verifier.verify(givenTokens[1]);
    }

    public String getSecretKey() {
        return secretKey;
    }
}
