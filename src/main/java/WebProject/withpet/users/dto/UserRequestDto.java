package WebProject.withpet.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    String nickname;
    String email;
    String password;

    @Builder
    public UserRequestDto(
            String nickname,
            String email,
            String password
    ) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}
