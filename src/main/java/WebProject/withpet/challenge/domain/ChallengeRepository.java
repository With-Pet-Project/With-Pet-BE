package WebProject.withpet.challenge.domain;

import WebProject.withpet.pets.domain.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findChallengesByPet(Pet pet);
}
