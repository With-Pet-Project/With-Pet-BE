package WebProject.withpet.users.dto;

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

    @NotBlank(message = "변경할 비밀번호를 입력하세요")
    private String password;
}
