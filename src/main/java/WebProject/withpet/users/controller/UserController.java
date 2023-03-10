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
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
    public ResponseEntity<ApiResponse<String>> signIn(@Valid @RequestBody LoginVo request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        TokenResponseDto tokenResponseDto = userService.login(request.getEmail(), request.getPassword());
        response.addHeader("Set-Cookie", createCookie(tokenResponseDto).toString());

        ApiResponse<String> apiResponse = new ApiResponse<>(200, "????????? ???????????????.", tokenResponseDto.getAccessToken());
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login/kakao")
    public ResponseEntity<ApiResponse<SocialLoginResponseDto>> socialLogin(
            @RequestParam(name = "code") @NotBlank(message = "?????? ?????? ?????? ???????????????.") String code,
            @Valid @RequestBody SocialLoginRequestDto dto, HttpServletResponse response)
            throws JSONException, UnsupportedEncodingException {

        TokenResponseDto tokenResponseDto = userService.socialLogin(code, dto);
        response.addHeader("Set-Cookie", createCookie(tokenResponseDto).toString());

        ApiResponse<SocialLoginResponseDto> socialLongResponse = new ApiResponse<>(200, "????????? ????????? ??????",
                SocialLoginResponseDto.builder().token(tokenResponseDto.getAccessToken()).build());

        return ResponseEntity.status(HttpStatus.OK).body(socialLongResponse);

    }

    @GetMapping("/duplicate-check")
    public ResponseEntity<ApiResponse<Void>> duplicateCheck(
            @RequestParam("nickName") @NotBlank(message = "???????????? ???????????????") String nickName) {
        userService.validateDuplicateNickname(nickName);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "?????? ????????? ??????????????????"));
    }

    @PostMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                            @RequestBody @Valid ChangePasswordDto changePasswordDto) {

        if (principalDetails != null) {
            //????????? x??? ?????????
            userService.changePassword(principalDetails.getUser(), changePasswordDto);
        } else {
            userService.changePassword(null, changePasswordDto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstants.RESPONSE_UPDATE_OK);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<Void>> deleteUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        userService.deleteUser(principalDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstants.RESPONSE_DEL_OK);
    }

    private ResponseCookie createCookie(TokenResponseDto tokenResponseDto) throws UnsupportedEncodingException {
        return ResponseCookie.from(JwtTokenProvider.REFRESH_TOKEN_HEADER_STRING, tokenResponseDto.getRefreshToken())
                .path("/").sameSite("None").secure(true).maxAge(Math.toIntExact(cookieValidTime)).httpOnly(true)
                .build();
    }
}
