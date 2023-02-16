package WebProject.withpet.challenge.domain;

import static WebProject.withpet.challenge.domain.QChallengeLog.challengeLog;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChallengeLogRepositoryImpl implements ChallengeLogRepositoryCustom {
    private final EntityManager em;

    public List<Tuple> getLogs(Long challengeId, int year, int month, int week) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(challengeLog.year, challengeLog.month, challengeLog.week, challengeLog.day,
                        challengeLog.id)
                .from(challengeLog)
                .where(challengeLog.challenge.id.eq(challengeId),
                        challengeLog.year.eq(year), challengeLog.month.eq(month),
                        challengeLog.week.eq(week))
                .fetch();
    }
}
