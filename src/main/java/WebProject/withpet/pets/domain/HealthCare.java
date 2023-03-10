package WebProject.withpet.pets.domain;

import WebProject.withpet.common.domain.BaseEntity;
import javax.persistence.Column;
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
@Table(name = "health_cares")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthCare extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Column(name = "walk_distance")
    private double walkDistance;

    private double weight;

    @Column(name = "drink_amount")
    private double drinkAmount;

    @Column(name = "feed_amount")
    private double feedAmount;

    private int year;
    private int month;
    private int week;
    private int day;

    public void update(HealthCare updateHealthCare) {
        pet = updateHealthCare.pet;
        walkDistance = updateHealthCare.walkDistance;
        weight = updateHealthCare.weight;
        drinkAmount = updateHealthCare.drinkAmount;
        feedAmount = updateHealthCare.feedAmount;
    }
}
