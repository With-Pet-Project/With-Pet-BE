package WebProject.withpet.challenge.domain;

import WebProject.withpet.common.domain.BaseEntity;
import WebProject.withpet.pets.domain.Pet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "unique_column_in_challenge_title",
                columnNames = {"title", "pet_id"}
        )})
public class Challenge extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    @NotNull
    private Pet pet;

    @Size(min = 1)
    @NotNull(message = "챌린지 제목은 빈 칸으로 둘 수 없습니다.")
    private String title;

    @Min(value = 1, message = "챌린지는 일주일에 최소 1번 이상 수행해야 합니다.")
    @Max(value = 7, message = "챌린지는 일주일에 최대 7번 수행할 수 있습니다.")
    @Column(name = "target_cnt")
    private int targetCnt;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<ChallengeLog> logs = new ArrayList<>();

    public void update(Challenge updateChallenge) {
        pet = updateChallenge.getPet();
        title = updateChallenge.getTitle();
        targetCnt = updateChallenge.getTargetCnt();
    }

    @Builder
    public Challenge(Pet pet, String title, int targetCnt) {
        this.pet = pet;
        this.title = title;
        this.targetCnt = targetCnt;
    }
}
