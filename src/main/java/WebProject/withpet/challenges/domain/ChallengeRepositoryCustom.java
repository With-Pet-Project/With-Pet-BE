package WebProject.withpet.challenges.domain;

import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.users.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepositoryCustom {
    boolean isDuplicateTitleChallenge(Pet pet, String title);
}
