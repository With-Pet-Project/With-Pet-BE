package WebProject.withpet.challenges;

import WebProject.withpet.challenges.domain.Challenge;
import WebProject.withpet.challenges.domain.ChallengeRepository;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.pets.domain.PetRepository;
import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.repository.UserRepository;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChallengeTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    ChallengeRepository challengeRepository;

    private Pet pet;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .nickName("test")
                .password("test")
                .build();

        userRepository.save(user);

        Pet pet = Pet.builder()
                .user(user)
                .name("pet")
                .birthday(LocalDate.now())
                .initWeight(3.18)
                .build();

        petRepository.save(pet);
        this.pet = pet;
    }

    @Test
    @DisplayName(value = "반려동물과 챌린지 이름이 중복되는 경우 오류가 발생하는지 테스트한다.")
    public void checkUniqueConstraintWithDuplicateChallengeName() {
        Challenge challenge = new Challenge(this.pet, "test", 3);
        challengeRepository.save(challenge);

        Challenge duplicateChallenge = new Challenge(this.pet, "test", 3);
        Assertions.assertThatThrownBy(() -> challengeRepository.save(duplicateChallenge));
    }
}
