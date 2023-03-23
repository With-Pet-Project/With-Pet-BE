package WebProject.withpet.challenges.service;

import WebProject.withpet.challenges.domain.Challenge;
import WebProject.withpet.challenges.domain.ChallengeLog;
import WebProject.withpet.challenges.domain.ChallengeLogRepository;
import WebProject.withpet.challenges.dto.ChallengeLogRequestDto;
import WebProject.withpet.common.constants.ErrorCode;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.DuplicateDateException;
import WebProject.withpet.pets.service.PetService;
import WebProject.withpet.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeLogService {
    private final ChallengeLogRepository challengeLogRepository;
    private final ChallengeService challengeService;
    private final PetService petService;

    public void registerChallengeLog(Long petId, Long challengeId, User user, ChallengeLogRequestDto requestDto) {
        Challenge challenge = challengeService.accessChallenge(petId, challengeId, user);
        if (isDuplicateChallengeLog(challenge, requestDto)) {
            throw new DuplicateDateException(ErrorCode.DUPLICATE_DATE);
        }
        challengeLogRepository.save(requestDto.toEntity(challenge));
    }

    public void deleteChallengeLog(Long petId, Long logId, User user) {
        ChallengeLog challengeLog = accessChallengeLog(logId, petId, user);
        challengeLogRepository.delete(challengeLog);
    }

    private ChallengeLog findChallengeLogById(Long id) {
        return challengeLogRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    private ChallengeLog accessChallengeLog(Long id, Long petId, User user) {
        petService.accessPet(user, petId);
        return findChallengeLogById(id);
    }

    private boolean isDuplicateChallengeLog(Challenge challenge, ChallengeLogRequestDto requestDto) {
        return challengeLogRepository.isDuplicateChallengeLog(challenge, requestDto.getYear(), requestDto.getMonth(),
                requestDto.getWeek(), requestDto.getDay());
    }
}
