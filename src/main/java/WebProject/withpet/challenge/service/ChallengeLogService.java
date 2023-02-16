package WebProject.withpet.challenge.service;

import WebProject.withpet.challenge.domain.Challenge;
import WebProject.withpet.challenge.domain.ChallengeLog;
import WebProject.withpet.challenge.domain.ChallengeLogRepository;
import WebProject.withpet.challenge.domain.ChallengeLogRepositoryImpl;
import WebProject.withpet.challenge.domain.ChallengeRepository;
import WebProject.withpet.challenge.dto.ChallengeLogRequestDto;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.UnauthorizedException;
import WebProject.withpet.pets.service.PetService;
import WebProject.withpet.users.domain.User;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeLogService {
    private final ChallengeLogRepository challengeLogRepository;
    private final ChallengeLogRepositoryImpl challengeLogRepositoryImpl;
    private final ChallengeRepository challengeRepository;
    private final ChallengeService challengeService;
    private final PetService petService;

    public void registerChallengeLog(Long challengeId, User user, ChallengeLogRequestDto requestDto) {
        Challenge challenge = challengeService.accessChallenge(challengeId, user);
        challengeLogRepository.save(requestDto.toEntity(challenge));
    }

    public void deleteChallengeLog(Long logId, User user) {
        ChallengeLog challengeLog = accessChallengeLog(logId, user);
        challengeLogRepository.delete(challengeLog);
    }

    private ChallengeLog findChallengeLogById(Long id) {
        return challengeLogRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    private void checkPermission(User user, ChallengeLog challengeLog) {
        if (!Objects.equals(challengeLog.getChallenge().getUser().getId(), user.getId())) {
            throw new UnauthorizedException();
        }
    }

    private ChallengeLog accessChallengeLog(Long id, User user) {
        ChallengeLog challengeLog = findChallengeLogById(id);
        checkPermission(user, challengeLog);
        return challengeLog;
    }
}
