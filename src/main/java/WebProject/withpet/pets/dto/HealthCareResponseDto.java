package WebProject.withpet.pets.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HealthCareResponseDto {
    private Long id;
    private double walkDistance;
    private double weight;
    private double drinkAmount;
    private double feedAmount;
    private String diary;

    private int year;
    private int month;
    private int week;
    private int day;
}
