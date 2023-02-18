package WebProject.withpet.challenges.dto;

import WebProject.withpet.challenges.domain.Challenge;
import WebProject.withpet.challenges.domain.ChallengeLog;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
@Builder
public class ChallengeLogRequestDto {

    @NotNull
    private int year;

    @NotNull
    private int month;

    @NotNull
    private int week;

    @NotNull
    private int day;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;

    public ChallengeLog toEntity(
            Challenge challenge
    ) {
        return ChallengeLog.builder()
                .challenge(challenge)
                .year(year)
                .month(month)
                .week(week)
                .day(day)
                .date(date)
                .build();
    }
}
