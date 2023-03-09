package WebProject.withpet.users.dto;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {

    @Email(message = "이메일 형식이 아닙니다")
    private String email;

    @NotBlank(message = "변경할 비밀번호를 입력하세요")
    private String password;
}
