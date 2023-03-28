package WebProject.withpet.pets.domain;

import static WebProject.withpet.pets.domain.QPet.pet;

import WebProject.withpet.users.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PetRepositoryImpl implements PetRepositoryCustom {
    private final EntityManager em;

    @Override
    public boolean isDuplicateNamePet(User user, String petName) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory.from(pet).where(pet.name.eq(petName), pet.user.eq(user)).fetchFirst() != null;
    }
}
