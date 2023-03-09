package WebProject.withpet.pets.dto;

import WebProject.withpet.pets.domain.Diary;
import WebProject.withpet.pets.domain.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryRequestDto {
    private String content;
    private int year;
    private int month;
    private int week;
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
