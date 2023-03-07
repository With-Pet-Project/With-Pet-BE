package WebProject.withpet.auth.controller;

import WebProject.withpet.auth.service.EmailService;
import WebProject.withpet.common.dto.ApiResponse;
import WebProject.withpet.users.service.UserService;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import javax.mail.MessagingException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    @PostMapping("/request-confirm")
    public ResponseEntity<ApiResponse<Void>> requestConfirmEmail(@RequestParam @NotBlank @Email String requestEmail)
            throws MessagingException, UnsupportedEncodingException {
        String confirmationKey = userService.generateConfirmationToken(requestEmail, LocalDateTime.now());
        emailService.sendEmail(requestEmail, confirmationKey);
        return ResponseEntity.ok(new ApiResponse<>(200, "정상적으로 이메일이 전송되었습니다.", null));
    }

    @GetMapping("/check-confirm")
    public ResponseEntity<ApiResponse<Void>> checkConfirmEmail(@RequestParam @NotBlank @Email String requestEmail,
                                                               @RequestParam @NotBlank String key) {
        userService.permissionCheckByConfirmationToken(requestEmail, key, LocalDateTime.now());
        return ResponseEntity.ok(new ApiResponse<>(200, "인증된 사용자입니다.", null));
    }
}
