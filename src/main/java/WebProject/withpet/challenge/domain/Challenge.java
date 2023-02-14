package WebProject.withpet.challenge.domain;

import WebProject.withpet.common.domain.BaseEntity;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.users.domain.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "challenges")
@Getter
@NoArgsConstructor
public class Challenge extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

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

    public void update(Challenge updateChallenge) {
        pet = updateChallenge.getPet();
        title = updateChallenge.getTitle();
        targetCnt = updateChallenge.getTargetCnt();
    }

    @Builder
    public Challenge(
            User user,
            Pet pet,
            String title,
            int targetCnt
    ) {
        this.user = user;
        this.pet = pet;
        this.title = title;
        this.targetCnt = targetCnt;
    }
}
