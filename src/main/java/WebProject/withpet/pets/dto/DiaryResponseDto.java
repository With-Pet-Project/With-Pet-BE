package WebProject.withpet.pets.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryResponseDto {
    private Long id;
    private String content;
    private int year;
    private int month;
    private int week;
    private int day;
}
