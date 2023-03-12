package WebProject.withpet.users.dto;


import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginRequestDto {

    @NotBlank(message = "URI는 필수 값입니다.")
    private String redirectURI;
}
