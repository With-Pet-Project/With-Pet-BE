package WebProject.withpet.pets.domain;

import static WebProject.withpet.pets.domain.QConsumption.consumption;
import static WebProject.withpet.pets.domain.QPet.pet;

import WebProject.withpet.pets.dto.ConsumptionResponseDto;
import WebProject.withpet.users.domain.QUser;
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
    public List<ConsumptionResponseDto> findMonthlyConsumptionsByUser(User user, int year, int month) {
        QUser qUser = QUser.user;

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(
                        Projections.fields(ConsumptionResponseDto.class, consumption.id, consumption.pet.id.as("petId"),
                                consumption.toy, consumption.hospital,
                                consumption.beauty, consumption.etc, consumption.feed, consumption.day)).from(consumption)
                .where(consumption.pet.id.in(findPetsByUser(user)), consumption.year.eq(year),
                        consumption.month.eq(month)).fetch();
    }

    public boolean isDuplicateDateConsumption(Pet pet, int year, int month, int week, int day) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory.from(consumption)
                .where(consumption.pet.eq(pet), consumption.year.eq(year), consumption.month.eq(month),
                        consumption.week.eq(week), consumption.day.eq(day)).fetchFirst()
                != null;
    }

    private List<Long> findPetsByUser(User user) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QUser qUser = QUser.user;
        return queryFactory.select(pet.id)
                .from(pet).where(pet.user.id.eq(user.getId()))
                .fetch();
    }
}
