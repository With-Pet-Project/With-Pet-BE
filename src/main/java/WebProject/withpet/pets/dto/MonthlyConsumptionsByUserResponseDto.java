package WebProject.withpet.pets.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyConsumptionsByUserResponseDto {
    private int day;
    List<ConsumptionResponseDto> consumptions;

    public MonthlyConsumptionsByUserResponseDto(int day) {
        this.day = day;
    }
}
