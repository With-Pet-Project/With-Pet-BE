package WebProject.withpet.auth.filter;

import WebProject.withpet.auth.PrincipalDetails;
import WebProject.withpet.auth.application.JwtTokenProvider;
import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.dto.LoginVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper = new ObjectMapper();
    private final JwtTokenProvider jwtTokenProvider;

    // 유효한 사용자인지 확인
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            ObjectMapper om = new ObjectMapper();
            LoginVo user = om.readValue(request.getInputStream(), LoginVo.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getEmail(), user.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 로그인에 성공할 시에 해당 토큰을 반환
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        User userEntity = principalDetails.getUser();
        String jwtToken = jwtTokenProvider.createToken(userEntity);

        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> json = new HashMap<>();
        json.put("code", "200");
        json.put("message", "정상적으로 토큰이 발급되었습니다");
        json.put("data", jwtToken);
        String jsonResponse = mapper.writeValueAsString(json);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(jsonResponse);
    }
}
