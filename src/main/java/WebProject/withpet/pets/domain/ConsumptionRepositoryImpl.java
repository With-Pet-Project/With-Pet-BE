package WebProject.withpet.pets.domain;

import static WebProject.withpet.pets.domain.QConsumption.consumption;
import static WebProject.withpet.pets.domain.QPet.pet;

import WebProject.withpet.pets.dto.ConsumptionResponseDto;
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
                        Projections.fields(ConsumptionResponseDto.class, consumption.id, consumption.pet.id.as("petId"),
                                consumption.toy, consumption.hospital,
                                consumption.beauty, consumption.etc, consumption.feed, consumption.day)).from(consumption)
                .where(consumption.pet.eq(pet), consumption.year.eq(year), consumption.month.eq(month)).fetch();
    }

    @Override
    public List<ConsumptionResponseDto> findMonthlyConsumptionsByUser(User user, int year, int month, int day) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(
                        Projections.fields(ConsumptionResponseDto.class, consumption.id, consumption.pet.id.as("petId"),
                                consumption.toy, consumption.hospital,
                                consumption.beauty, consumption.etc, consumption.feed, consumption.day)).from(consumption)
                .leftJoin(consumption.pet, pet).on(pet.user.id.eq(user.getId()))
                .where(consumption.year.eq(year), consumption.month.eq(month), consumption.month.eq(day)).fetch();
    }
}
