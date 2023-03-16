package WebProject.withpet.auth.controller;

import static WebProject.withpet.auth.application.JwtTokenProvider.REFRESH_TOKEN_HEADER_STRING;
import static WebProject.withpet.auth.util.JwtUtil.COOKIE_NAME;

import WebProject.withpet.auth.dto.TokenResponseDto;
import WebProject.withpet.auth.service.TokenService;
import WebProject.withpet.auth.util.JwtUtil;
import WebProject.withpet.common.dto.ApiResponse;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reissue")
public class AccessTokenController {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @GetMapping
    public ResponseEntity<ApiResponse<String>> getNewAccessToken(
            @CookieValue(name = REFRESH_TOKEN_HEADER_STRING) String refreshToken, HttpServletResponse response) {
        Long userId = jwtUtil.getUserId(refreshToken);
        TokenResponseDto responseDto = tokenService.reissueToken(refreshToken, userId, new Date());
        String newRefreshToken = responseDto.getRefreshToken();
        if (responseDto.getRefreshToken() != null) {
            ResponseCookie cookie = jwtUtil.createCookie(newRefreshToken);
            response.addHeader(COOKIE_NAME, cookie.toString());
        }

        return ResponseEntity.ok(new ApiResponse<>(200, "새로운 액세스 토큰이 발급되었습니다.", responseDto.getAccessToken()));
    }
}
