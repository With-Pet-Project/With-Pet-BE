package WebProject.withpet.pets.dto;

import WebProject.withpet.pets.domain.Pet;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PetResponseDto {
    private Long id;

    private final String name;

    private final Double initWeight;

    private LocalDate birthday;

    public PetResponseDto(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.initWeight = pet.getInitWeight();
        this.birthday = pet.getBirthday();
    }
}