package WebProject.withpet.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginVo {
    String email;
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
