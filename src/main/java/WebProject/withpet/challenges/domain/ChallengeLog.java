package WebProject.withpet.challenges.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "challenge_logs")
@Getter
@NoArgsConstructor
public class ChallengeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    @NotNull
    private Challenge challenge;

    // 달성 일자
    @NotNull
    private int year;
    @NotNull
    private int month;
    @NotNull
    private int day;
    @NotNull
    // 몇번째 주
    private int week;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate date;

    @Builder
    public ChallengeLog(
            Challenge challenge,
            int year,
            int month,
            int day,
            int week,
            LocalDate date
    ) {
        this.challenge = challenge;
        this.year = year;
        this.month = month;
        this.day = day;
        this.week = week;
        this.date = date;
    }
}
