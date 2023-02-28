package WebProject.withpet.pets.domain;

import WebProject.withpet.pets.dto.ConsumptionResponseDto;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionRepositoryCustom {
    List<ConsumptionResponseDto> findMonthlyConsumptions(Pet pet, int year, int month);
}
