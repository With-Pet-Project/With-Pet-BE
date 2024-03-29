package WebProject.withpet.challenges.service;

import static WebProject.withpet.challenges.domain.QChallengeLog.challengeLog;

import WebProject.withpet.challenges.domain.Challenge;
import WebProject.withpet.challenges.domain.ChallengeLogRepositoryImpl;
import WebProject.withpet.challenges.domain.ChallengeRepository;
import WebProject.withpet.challenges.dto.ChallengeRequestDto;
import WebProject.withpet.challenges.dto.DailyChallengeResponseDto;
import WebProject.withpet.challenges.dto.WeeklyChallengeResponseDto;
import WebProject.withpet.common.constants.ErrorCode;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.DuplicateException;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.pets.service.PetService;
import WebProject.withpet.users.domain.User;
import com.querydsl.core.Tuple;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final ChallengeLogRepositoryImpl challengeLogRepositoryImpl;
    private final PetService petService;

    @Transactional
    public void registerChallenge(Long petId, User user, ChallengeRequestDto requestDto) {
        Pet pet = petService.accessPet(user, petId);
        if (isDuplicateChallenge(pet, requestDto.getTitle())) {
            throw new DuplicateException(ErrorCode.DUPLICATE_CHALLENGE);
        }
        challengeRepository.save(requestDto.toEntity(pet));
    }

    @Transactional
    public void updateChallenge(Long petId, Long challengeId, User user, ChallengeRequestDto requestDto) {
        Pet pet = petService.accessPet(user, petId);
        Challenge challenge = accessChallenge(petId, challengeId, user);
        challenge.update(requestDto.toEntity(pet));
    }

    @Transactional
    public void deleteChallenge(Long petId, Long challengeId, User user) {
        Challenge challenge = accessChallenge(petId, challengeId, user);
        challengeRepository.delete(challenge);
    }

    public List<DailyChallengeResponseDto> getDailyChallenges(Long petId, User user, int year, int month, int week,
                                                              @Nullable int day) {
        Pet pet = petService.accessPet(user, petId);
        List<Challenge> challenges = challengeRepository.findChallengesByPet(pet);

        List<DailyChallengeResponseDto> result = new ArrayList<>();
        for (Challenge challenge : challenges) {
            List<Tuple> logs = challengeLogRepositoryImpl.getLogs(challenge.getId(), year, month, week);
            DailyChallengeResponseDto response = new DailyChallengeResponseDto(challenge, getAchieveCnt(logs),
                    getAchievedLogId(logs, year, month, week, day), pet.getId());
            result.add(response);
        }
        return result;
    }

    public List<WeeklyChallengeResponseDto> getWeeklyChallenges(Long petId, User user, int year, int month, int week) {
        Pet pet = petService.accessPet(user, petId);
        List<Challenge> challenges = challengeRepository.findChallengesByPet(pet);

        List<WeeklyChallengeResponseDto> result = new ArrayList<>();
        for (Challenge challenge : challenges) {
            List<Tuple> logs = challengeLogRepositoryImpl.getLogs(challenge.getId(), year, month, week);
            WeeklyChallengeResponseDto response = new WeeklyChallengeResponseDto(challenge, getAchieveCnt(logs),
                    pet.getId());
            result.add(response);
        }
        return result;
    }

    private Long getAchievedLogId(List<Tuple> logs, int year, int month, int week, int day) {
        for (Tuple tuple : logs) {
            if (tuple.get(challengeLog.year).equals(year) && tuple.get(challengeLog.month).equals(month) && tuple.get(
                    challengeLog.week).equals(week) && tuple.get(challengeLog.day).equals(day)) {
                return tuple.get(challengeLog.id);
            }
        }
        return null;
    }

    private int getAchieveCnt(List<Tuple> logs) {
        return logs.size();
    }

    private Challenge findChallengeById(Long id) throws DataNotFoundException {
        return challengeRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    public Challenge accessChallenge(Long petId, Long challengeId, User user) {
        petService.accessPet(user, petId);
        return findChallengeById(challengeId);
    }

    private boolean isDuplicateChallenge(Pet pet, String title) {
        return challengeRepository.isDuplicateTitleChallenge(pet, title);
    }
}
