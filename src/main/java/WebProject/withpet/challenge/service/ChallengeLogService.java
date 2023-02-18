package WebProject.withpet.challenge.service;

import WebProject.withpet.challenge.domain.Challenge;
import WebProject.withpet.challenge.domain.ChallengeLog;
import WebProject.withpet.challenge.domain.ChallengeLogRepository;
import WebProject.withpet.challenge.domain.ChallengeLogRepositoryImpl;
import WebProject.withpet.challenge.domain.ChallengeRepository;
import WebProject.withpet.challenge.dto.ChallengeLogRequestDto;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.pets.service.PetService;
import WebProject.withpet.users.domain.User;
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

    public void registerChallengeLog(Long petId, Long challengeId, User user, ChallengeLogRequestDto requestDto) {
        Challenge challenge = challengeService.accessChallenge(petId, challengeId, user);
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
}
