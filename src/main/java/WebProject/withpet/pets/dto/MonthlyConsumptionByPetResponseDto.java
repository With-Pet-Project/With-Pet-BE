package WebProject.withpet.pets.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MonthlyConsumptionByPetResponseDto {
    private int day;
    private ConsumptionResponseDto consumption;

    public MonthlyConsumptionByPetResponseDto(
            ConsumptionResponseDto consumption
    ) {
        day = consumption.getDay();
        this.consumption = consumption;
    }
}
