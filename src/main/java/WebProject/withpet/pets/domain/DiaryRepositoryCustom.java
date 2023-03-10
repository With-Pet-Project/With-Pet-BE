package WebProject.withpet.pets.domain;

import WebProject.withpet.pets.dto.DiaryResponseDto;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepositoryCustom {
    List<DiaryResponseDto> getMonthlyDiaries(Pet pet, int year, int month);
}
