package WebProject.withpet.pets.domain;

import WebProject.withpet.pets.dto.HealthCareResponseDto;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCareRepositoryCustom {
    List<HealthCareResponseDto> findMonthlyHealthCares(Pet pet, int year, int month);
}
