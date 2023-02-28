package WebProject.withpet.pets.domain;

import static WebProject.withpet.pets.domain.QConsumption.consumption;

import WebProject.withpet.pets.dto.ConsumptionResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConsumptionRepositoryImpl implements ConsumptionRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<ConsumptionResponseDto> findMonthlyConsumptions(Pet pet, int year, int month) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(
                        Projections.fields(ConsumptionResponseDto.class, consumption.id, consumption.toy, consumption.hospital,
                                consumption.beauty, consumption.etc, consumption.year, consumption.month, consumption.week,
                                consumption.day)).from(consumption)
                .where(consumption.pet.eq(pet), consumption.year.eq(year), consumption.month.eq(month)).fetch();
    }
}
