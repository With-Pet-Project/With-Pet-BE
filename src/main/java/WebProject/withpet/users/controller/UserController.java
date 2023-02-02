package WebProject.withpet.users.controller;

import WebProject.withpet.common.auth.application.JwtTokenProvider;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.dto.ApiResponse;
import WebProject.withpet.users.dto.UserRequestDto;
import WebProject.withpet.users.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;


    @PostMapping("/signup")
    public ApiResponse<Void> signUp(@Valid @RequestBody UserRequestDto user) {
        userService.register(user);
        return ResponseConstants.RESPONSE_SAVE_OK;
    }
}
