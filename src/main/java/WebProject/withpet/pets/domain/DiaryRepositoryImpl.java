package WebProject.withpet.pets.domain;

import static WebProject.withpet.pets.domain.QDiary.diary;

import WebProject.withpet.pets.dto.DiaryResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<DiaryResponseDto> getMonthlyDiaries(Pet pet, int year, int month) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(
                        Projections.fields(DiaryResponseDto.class, diary.id, diary.pet.id.as("petId"), diary.content,
                                diary.year, diary.month, diary.week, diary.day)).from(diary)
                .where(diary.pet.eq(pet), diary.year.eq(year), diary.month.eq(month)).fetch();
    }

    @Override
    public boolean isDuplicateDateDiary(Pet pet, int year, int month, int week, int day) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory.from(diary)
                .where(diary.pet.eq(pet), diary.year.eq(year), diary.month.eq(month),
                        diary.week.eq(week), diary.day.eq(day)).fetchFirst()
                != null;
    }
}
