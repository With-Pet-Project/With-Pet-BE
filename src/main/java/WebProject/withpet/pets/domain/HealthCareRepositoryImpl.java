package WebProject.withpet.pets.domain;

import static WebProject.withpet.pets.domain.QHealthCare.healthCare;

import WebProject.withpet.pets.dto.HealthCareResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HealthCareRepositoryImpl implements HealthCareRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<HealthCareResponseDto> findMonthlyHealthCares(Pet pet, int year, int month) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(
                        Projections.fields(HealthCareResponseDto.class, healthCare.id, healthCare.walkDistance,
                                healthCare.weight, healthCare.drinkAmount, healthCare.feedAmount, healthCare.year,
                                healthCare.month, healthCare.week, healthCare.day)).from(healthCare)
                .where(healthCare.pet.eq(pet), healthCare.year.eq(year), healthCare.month.eq(month)).fetch();
    }

    @Override
    public boolean isDuplicateDateHealthCare(Pet pet, int year, int month, int week, int day) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory.from(healthCare)
                .where(healthCare.pet.eq(pet), healthCare.year.eq(year), healthCare.month.eq(month),
                        healthCare.week.eq(week), healthCare.day.eq(day)).fetchFirst() != null;
    }
}
