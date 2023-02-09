package WebProject.withpet.pets.dto;

import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.users.domain.User;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PetRequestDto {
    private final String name;

    private final Double initWeight;

    private LocalDate birthday;

    public Pet toEntity(
            User user
    ) {
        return Pet.builder()
                .user(user)
                .name(name)
                .initWeight(initWeight)
                .birthday(birthday)
                .build();
    }
}
