package WebProject.withpet.challenge.dto;

import WebProject.withpet.challenge.domain.Challenge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class WeeklyChallengeResponseDto {
    private Long challengeId;

    private Long petId;
    private String title;
    private int targetCnt;
    private int achieveCnt;

    public WeeklyChallengeResponseDto(
            Challenge challenge,
            int achieveCnt,
            Long petId
    ) {
        this.challengeId = challenge.getId();
        this.petId = petId;
        this.title = challenge.getTitle();
        this.targetCnt = challenge.getTargetCnt();
        this.achieveCnt = achieveCnt;
    }
}
