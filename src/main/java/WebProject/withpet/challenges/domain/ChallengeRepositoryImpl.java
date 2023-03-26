package WebProject.withpet.challenges.domain;

import static WebProject.withpet.challenges.domain.QChallenge.challenge;

import WebProject.withpet.pets.domain.Pet;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChallengeRepositoryImpl implements ChallengeRepositoryCustom {
    private final EntityManager em;

    @Override
    public boolean isDuplicateTitleChallenge(Pet pet, String title) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.from(challenge).where(challenge.pet.eq(pet), challenge.title.eq(title)).fetchFirst()
                != null;
    }
}
