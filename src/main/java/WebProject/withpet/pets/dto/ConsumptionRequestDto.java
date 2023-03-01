package WebProject.withpet.pets.dto;

import WebProject.withpet.pets.domain.Consumption;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.users.domain.User;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ConsumptionRequestDto {
    private final Long feed;

    private Long toy;

    private Long hospital;

    private Long beauty;

    private Long etc;

    @Min(2023)
    private int year;
    @Min(1)
    @Max(12)
    private int month;
    @Min(1)
    @Max(5)
    private int week;

    @Min(1)
    @Max(31)
    private int day;

    public Consumption toEntity(User user, Pet pet) {
        return Consumption.builder().pet(pet).user(user).feed(feed).toy(toy).hospital(hospital).beauty(beauty).etc(etc)
                .year(year).month(month).week(week).day(day).build();
    }
}
