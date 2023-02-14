package WebProject.withpet.challenge.service;

import WebProject.withpet.challenge.domain.Challenge;
import WebProject.withpet.challenge.domain.ChallengeRepository;
import WebProject.withpet.challenge.dto.ChallengeRequestDto;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.UnauthorizedException;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.pets.service.PetService;
import WebProject.withpet.users.domain.User;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final PetService petService;

    @Transactional
    public void registerChallenge(User user, ChallengeRequestDto requestDto) {
        Pet pet = petService.accessPet(user, requestDto.getPetId());
        challengeRepository.save(requestDto.toEntity(user, pet));
    }

    @Transactional
    public void updateChallenge(Long challengeId, User user, ChallengeRequestDto requestDto) {
        Challenge challenge = accessChallenge(challengeId, user);
        Pet pet = petService.accessPet(user, requestDto.getPetId());
        challenge.update(requestDto.toEntity(user, pet));

    }

    @Transactional
    public void deleteChallenge(Long challengeId, User user) {
        Challenge challenge = accessChallenge(challengeId, user);
        challengeRepository.delete(challenge);
    }

    private Challenge findChallengeById(Long id) throws DataNotFoundException {
        return challengeRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    private void checkPermission(User user, Challenge challenge) {
        if (!Objects.equals(challenge.getUser().getId(), user.getId())) {
            throw new UnauthorizedException();
        }
    }

    public Challenge accessChallenge(Long id, User user) {
        Challenge challenge = findChallengeById(id);
        checkPermission(user, challenge);

        return challenge;
    }
}
