package WebProject.withpet.pets.dto;

import WebProject.withpet.pets.domain.Diary;
import WebProject.withpet.pets.domain.Pet;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryUpdateDto {
    private String content;
    @NotNull
    private Long petId;
    @Min(2023)
    private int year;
    @Min(1)
    @Max(12)
    private int month;
    @Min(0)
    @Max(5)
    private int week;
    @Min(1)
    @Max(31)
    private int day;

    public Diary toEntity(Pet pet) {
        return Diary.builder()
                .content(content)
                .pet(pet)
                .year(year)
                .month(month)
                .week(week)
                .day(day)
                .build();
    }
}
