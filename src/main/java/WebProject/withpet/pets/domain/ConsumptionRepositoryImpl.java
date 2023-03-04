package WebProject.withpet.pets.domain;

import static WebProject.withpet.pets.domain.QConsumption.consumption;

import WebProject.withpet.pets.dto.ConsumptionResponseDto;
import WebProject.withpet.pets.dto.MonthlyConsumptionByUserResponseDto;
import WebProject.withpet.users.domain.User;
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
    public List<ConsumptionResponseDto> findMonthlyConsumptionsByPet(Pet pet, int year, int month) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(
                        Projections.fields(ConsumptionResponseDto.class, consumption.id, consumption.toy, consumption.hospital,
                                consumption.beauty, consumption.etc, consumption.feed, consumption.day)).from(consumption)
                .where(consumption.pet.eq(pet), consumption.year.eq(year), consumption.month.eq(month)).fetch();
    }

    @Override
    public List<MonthlyConsumptionByUserResponseDto> getDailyConsumptionsByUser(User user, int year, int month) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(Projections.fields(MonthlyConsumptionByUserResponseDto.class, consumption.day,
                        consumption.toy.sum().as("toySum"), consumption.hospital.sum().as("hospitalSum"),
                        consumption.beauty.sum().as("beautySum"), consumption.etc.sum().as("etcSum"),
                        consumption.feed.sum().as("feedSum"))).from(consumption)
                .where(consumption.pet.user.eq(user), consumption.year.eq(year), consumption.month.eq(month))
                .groupBy(consumption.day).fetch();
    }

}
