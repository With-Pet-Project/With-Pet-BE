package WebProject.withpet.pets.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ConsumptionResponseDto {
    private Long id;

    private Long toy;
    private Long hospital;
    private Long beauty;
    private Long etc;

    private int year;
    private int month;
    private int week;
    private int day;
}
