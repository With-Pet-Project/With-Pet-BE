package WebProject.withpet.pets.domain;

import WebProject.withpet.challenges.domain.Challenge;
import WebProject.withpet.users.domain.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private String name;

    @Column(name = "init_weight")
    private double initWeight;

    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private LocalDate birthday;

    @OneToMany(mappedBy = "pet", orphanRemoval = true)
    private List<Challenge> challenges = new ArrayList<>();

    @OneToMany(mappedBy = "pet", orphanRemoval = true)
    private List<HealthCare> healthCares = new ArrayList<>();

    @OneToMany(mappedBy = "pet", orphanRemoval = true)
    private List<Consumption> consumptions = new ArrayList<>();

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
