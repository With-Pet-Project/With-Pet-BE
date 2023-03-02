package WebProject.withpet.pets.dto;

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

    private int day;
}
