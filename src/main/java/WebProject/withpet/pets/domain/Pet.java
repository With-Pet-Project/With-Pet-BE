package WebProject.withpet.pets.domain;

import WebProject.withpet.users.domain.User;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pets")
@Getter
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Size(min = 1)
    @NotNull(message = "반려동물의 이름은 빈칸으로 둘 수 없습니다.")
    private String name;

    @Column(name = "init_weight")
    private double initWeight;

    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private LocalDate birthday;

    @Builder
    public Pet(
            User user,
            String name,
            double initWeight,
            LocalDate birthday
    ) {
        this.user = user;
        this.name = name;
        this.initWeight = initWeight;
        this.birthday = birthday;
    }

    public void update(Pet updatePet) {
        this.name = updatePet.name;
        this.initWeight = updatePet.initWeight;
        this.birthday = updatePet.birthday;
    }
}
