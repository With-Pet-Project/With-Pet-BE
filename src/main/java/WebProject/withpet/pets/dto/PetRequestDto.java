package WebProject.withpet.pets.dto;

import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.users.domain.User;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
@Builder
public class PetRequestDto {
    @NotNull
    @Size(min = 1)
    private final String name;

    private final Double initWeight;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
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
