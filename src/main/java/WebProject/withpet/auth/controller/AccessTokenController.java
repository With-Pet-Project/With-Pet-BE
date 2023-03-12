package WebProject.withpet.auth.controller;

import static WebProject.withpet.auth.application.JwtTokenProvider.REFRESH_TOKEN_HEADER_STRING;

import WebProject.withpet.auth.application.JwtTokenProvider;
import WebProject.withpet.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/access")
public class AccessTokenController {

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<ApiResponse<String>> getNewAccessToken(

            @CookieValue(name = REFRESH_TOKEN_HEADER_STRING) String refreshToken) throws Exception {
        String newAccessToken = jwtTokenProvider.getNewAccessToken(refreshToken);
        return ResponseEntity.ok(new ApiResponse<>(200, "새로운 액세스 토큰이 발급되었습니다.", newAccessToken));
    }
}
