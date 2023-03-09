package WebProject.withpet.pets.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "diaries")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    private String content;

    private int year;
    private int month;
    private int week;
    private int day;

    public void update(Diary updatedDiary) {
        pet = updatedDiary.pet;
        content = updatedDiary.content;
        year = updatedDiary.year;
        month = updatedDiary.month;
        week = updatedDiary.week;
        day = updatedDiary.day;
    }
}