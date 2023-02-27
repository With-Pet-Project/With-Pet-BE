package WebProject.withpet.pets.dto;

import WebProject.withpet.pets.domain.HealthCare;
import WebProject.withpet.pets.domain.Pet;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class HealthCareRequestDto {
    private double walkDistance;
    private double weight;
    private double drinkAmount;
    private double feedAmount;
    private String diary;

    @NotNull
    private int year;
    @NotNull
    private int month;
    @NotNull
    private int week;
    @NotNull
    private int day;

    public HealthCare toEntity(Pet pet) {
        return HealthCare.builder()
                .pet(pet)
                .walkDistance(walkDistance)
                .weight(weight)
                .drinkAmount(drinkAmount)
                .feedAmount(feedAmount)
                .diary(diary)
                .year(year)
                .month(month)
                .week(week)
                .day(day)
                .build();
    }
}
