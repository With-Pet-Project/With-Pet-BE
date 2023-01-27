package WebProject.withpet.consumptions.domain;

import WebProject.withpet.config.BaseEntity;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.users.domain.User;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consumptions")
@Getter
@NoArgsConstructor
public class Comsumption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long feed;

    private Long toy;

    private Long hospital;

    private Long beauty;

    private Long etc;
}
