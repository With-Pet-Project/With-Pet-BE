package WebProject.withpet.users.controller;

import WebProject.withpet.auth.PrincipalDetails;
import WebProject.withpet.auth.application.JwtTokenProvider;
import WebProject.withpet.auth.dto.TokenResponseDto;
import WebProject.withpet.auth.vo.LoginVo;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.dto.ApiResponse;
import WebProject.withpet.users.dto.ChangePasswordDto;
import WebProject.withpet.users.dto.SocialLoginRequestDto;
import WebProject.withpet.users.dto.SocialLoginResponseDto;
import WebProject.withpet.users.dto.UserRequestDto;
import WebProject.withpet.users.service.UserService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Validated
public class UserController {

    @Value("${jwt.cookie-valid-time}")
    private Long cookieValidTime;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@Valid @RequestBody UserRequestDto user) {
        userService.register(user);
        return ResponseEntity.ok(ResponseConstants.RESPONSE_SAVE_OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> signIn(@Valid @RequestBody LoginVo request,
        HttpServletResponse response) {
        TokenResponseDto tokenResponseDto = userService.login(request.getEmail(),
            request.getPassword());

        Cookie cookie = new Cookie("refresh-token", tokenResponseDto.getRefreshToken());
        cookie.setMaxAge(Math.toIntExact(cookieValidTime));
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        ApiResponse<String> apiResponse = new ApiResponse<>(200, "로그인 되었습니다.",
            tokenResponseDto.getAccessToken());
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login/kakao")
    public ResponseEntity<ApiResponse<SocialLoginResponseDto>> socialLogin(
        @RequestParam(name = "code") @NotBlank(message = "인가 코드 값은 필수입니다.") String code,
        @Valid @RequestBody SocialLoginRequestDto dto,
        HttpServletResponse response)
        throws JSONException {

        TokenResponseDto tokenResponseDto = userService.socialLogin(code,dto);

        Cookie cookie = new Cookie("refresh-token", tokenResponseDto.getAccessToken());
        cookie.setMaxAge(Math.toIntExact(cookieValidTime));
        cookie.setHttpOnly(true);
        response.addCookie(cookie);


        ApiResponse<SocialLoginResponseDto> socialLongResponse = new ApiResponse<>(200,
            "카카오 로그인 성공",
            SocialLoginResponseDto.builder().token(tokenResponseDto.getAccessToken()).build());

        return ResponseEntity.status(HttpStatus.OK).body(socialLongResponse);

    }

    @GetMapping("/duplicate-check")
    public ResponseEntity<ApiResponse<Void>> duplicateCheck(
        @RequestParam("nickName") @NotBlank(message = "닉네임을 입력하시오") String nickName) {
        userService.validateDuplicateNickname(nickName);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "사용 가능한 닉네임입니다"));
    }

    @PostMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody @Valid ChangePasswordDto changePasswordDto) {

        if (principalDetails != null) {
            //로그인 x한 사용자
            userService.changePassword(principalDetails.getUser(), changePasswordDto);
        } else {
            userService.changePassword(null, changePasswordDto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstants.RESPONSE_UPDATE_OK);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<Void>> deleteUser(
        @AuthenticationPrincipal PrincipalDetails principalDetails) {

        userService.deleteUser(principalDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstants.RESPONSE_DEL_OK);
    }
}
