package WebProject.withpet.challenge.dto;

import WebProject.withpet.challenge.domain.Challenge;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.users.domain.User;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ChallengeRequestDto {

    @NotNull(message = "챌린지 대상 반려동물을 골라주세요.")
    private Long petId;
    @NotNull(message = "챌린지 제목은 빈 칸으로 둘 수 없습니다.")
    @Size(min = 1)
    private String title;
    @Min(value = 1, message = "챌린지는 일주일에 최소 1번 이상 수행해야 합니다.")
    @Max(value = 7, message = "챌린지는 일주일에 최대 7번 수행할 수 있습니다.")
    private int targetCnt;

    @Builder
    public Challenge toEntity(User user, Pet pet) {
        return Challenge.builder()
                .user(user)
                .pet(pet)
                .title(title)
                .targetCnt(targetCnt)
                .build();
    }
}
