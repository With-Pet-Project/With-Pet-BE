package WebProject.withpet.pets.dto;

import lombok.Getter;

@Getter
public class MonthlyConsumptionByUserResponseDto {
    private int day;

    private Long toySum;
    private Long hospitalSum;
    private Long beautySum;
    private Long etcSum;
    private Long feedSum;
}
