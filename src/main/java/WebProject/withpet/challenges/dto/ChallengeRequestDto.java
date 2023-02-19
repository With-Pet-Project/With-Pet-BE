package WebProject.withpet.challenges.dto;

import WebProject.withpet.challenges.domain.Challenge;
import WebProject.withpet.pets.domain.Pet;
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
    @NotNull(message = "챌린지 제목은 빈 칸으로 둘 수 없습니다.")
    @Size(min = 1)
    private String title;
    @Min(value = 1, message = "챌린지는 일주일에 최소 1번 이상 수행해야 합니다.")
    @Max(value = 7, message = "챌린지는 일주일에 최대 7번 수행할 수 있습니다.")
    private int targetCnt;

    @Builder
    public Challenge toEntity(Pet pet) {
        return Challenge.builder()
                .pet(pet)
                .title(title)
                .targetCnt(targetCnt)
                .build();
    }
}
