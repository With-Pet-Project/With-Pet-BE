package WebProject.withpet.pets.domain;

import WebProject.withpet.pets.dto.ConsumptionResponseDto;
import WebProject.withpet.pets.dto.MonthlyConsumptionByUserResponseDto;
import WebProject.withpet.users.domain.User;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionRepositoryCustom {
    List<ConsumptionResponseDto> findMonthlyConsumptionsByPet(Pet pet, int year, int month);

    List<MonthlyConsumptionByUserResponseDto> getDailyConsumptionsByUser(User user, int year, int month);
}
