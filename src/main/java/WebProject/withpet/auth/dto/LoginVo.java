package WebProject.withpet.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginVo {
    @NotBlank
    String email;
    @NotBlank
    String password;

    @Builder
    public LoginVo(
            String email,
            String password
    ) {
        this.email = email;
        this.password = password;
    }
}
