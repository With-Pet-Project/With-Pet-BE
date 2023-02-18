package WebProject.withpet.pets;

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
public class PetTest {

    private User user;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .nickName("test")
                .password("test")
                .build();

        userRepository.save(user);
        this.user = user;
    }

    @Test
    @DisplayName(value = "반려동물 이름이 중복되는 경우 오류가 발생하는지 테스트한다.")
    public void checkUniqueConstraintWithDuplicatePetName() {
        Pet pet = new Pet(user, "test", 3.18, LocalDate.now());
        petRepository.save(pet);

        Pet duplicatePet = new Pet(user, "test", 3.18, LocalDate.now());
        Assertions.assertThatThrownBy(() -> petRepository.save(duplicatePet));
    }
}