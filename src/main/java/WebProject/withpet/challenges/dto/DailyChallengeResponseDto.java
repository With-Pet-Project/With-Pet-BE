package WebProject.withpet.challenges.dto;

import WebProject.withpet.challenges.domain.Challenge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DailyChallengeResponseDto {
    private Long challengeId;
    private Long challengeLogId;
    private Long petId;
    private String title;
    private int targetCnt;
    private int achieveCnt;
    private boolean isAchieved;

    public DailyChallengeResponseDto(
            Challenge challenge,
            int achieveCnt, Long challengeLogId, Long petId
    ) {
        this.challengeId = challenge.getId();
        this.petId = petId;
        this.title = challenge.getTitle();
        this.targetCnt = challenge.getTargetCnt();
        this.achieveCnt = achieveCnt;
        this.challengeLogId = challengeLogId;
        this.isAchieved = challengeLogId != null;
    }
}
