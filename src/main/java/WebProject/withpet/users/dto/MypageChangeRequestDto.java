package WebProject.withpet.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageChangeRequestDto {

    @Nullable
    String profileImg;

    @Nullable
    String nickName;
}
