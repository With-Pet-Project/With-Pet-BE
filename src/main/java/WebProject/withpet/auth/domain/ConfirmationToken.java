package WebProject.withpet.auth.domain;

import WebProject.withpet.auth.util.ConfirmationKey;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "confirmation_tokens")
@Getter
@NoArgsConstructor
public class ConfirmationToken {

    @Value("${email.confirm-key-valid-time}")
    private static long EMAIL_TOKEN_EXPIRATION_TIME_VALUE;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    // 비밀번호 찾기를 요청한 사용자의 이메일
    @Email
    @NotBlank
    private String email;

    private LocalDateTime expiredAt;

    public static ConfirmationToken createConfirmationToken(String email, LocalDateTime requestedAt) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.email = email;
        confirmationToken.expiredAt = requestedAt.plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE);
        confirmationToken.content = ConfirmationKey.generateConfirmationKey();
        return confirmationToken;
    }

}
