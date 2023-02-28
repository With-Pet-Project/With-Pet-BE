package WebProject.withpet.pets.domain;

import WebProject.withpet.common.domain.BaseEntity;
import WebProject.withpet.users.domain.User;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consumptions")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Consumption extends BaseEntity {
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

    private int year;
    private int month;
    private int week;
    private int day;

    public void update(Consumption updateConsumption) {
        feed = updateConsumption.feed;
        toy = updateConsumption.toy;
        hospital = updateConsumption.hospital;
        beauty = updateConsumption.beauty;
        etc = updateConsumption.etc;
        year = updateConsumption.year;
        month = updateConsumption.month;
        week = updateConsumption.week;
        day = updateConsumption.day;
    }
}
