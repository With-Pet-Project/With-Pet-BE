package WebProject.withpet.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialUserInfoDto {

    String email;
    String nickname;

    @Builder
    public SocialUserInfoDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
