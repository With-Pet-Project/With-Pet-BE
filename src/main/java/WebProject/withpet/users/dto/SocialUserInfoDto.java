package WebProject.withpet.users.dto;

import WebProject.withpet.users.domain.User;
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

    public User toEntity(){

        return User.builder()
            .email(email)
            .nickName(nickname)
            .password("")
            .build();
    }
}
