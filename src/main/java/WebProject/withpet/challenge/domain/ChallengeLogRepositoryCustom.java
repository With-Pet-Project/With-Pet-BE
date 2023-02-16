package WebProject.withpet.challenge.domain;

import com.querydsl.core.Tuple;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeLogRepositoryCustom {
    List<Tuple> getLogs(Long challengeId, int year, int month, int week);

}
