package WebProject.withpet.pets.domain;

import WebProject.withpet.pets.dto.ConsumptionResponseDto;
import WebProject.withpet.users.domain.User;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionRepositoryCustom {
    List<ConsumptionResponseDto> findMonthlyConsumptionsByPet(Pet pet, int year, int month);

    List<ConsumptionResponseDto> findMonthlyConsumptionsByUser(User user, int year, int month, int day);
}
