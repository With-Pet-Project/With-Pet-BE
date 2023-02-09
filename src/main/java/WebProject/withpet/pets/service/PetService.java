package WebProject.withpet.pets.service;

import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.pets.domain.PetRepository;
import WebProject.withpet.pets.dto.PetRequestDto;
import WebProject.withpet.pets.dto.PetResponseDto;
import WebProject.withpet.users.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import javax.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    @Transactional
    public void registerPet(final PetRequestDto petRequest, final User user) {
        petRepository.save(petRequest.toEntity(user));
    }

    @Transactional
    public void updatePet(Long petId, User user, PetRequestDto petRequestDto) throws Exception {
        Pet pet = findPetById(petId);
        checkPermission(user, pet);

        Pet updatePet = petRequestDto.toEntity(user);
        pet.update(updatePet);
    }

    @Transactional
    public void deletePet(Long petId, User user) throws Exception {
        Pet pet = findPetById(petId);
        checkPermission(user, pet);

        petRepository.delete(pet);
    }

    @Transactional(readOnly = true)
    public List<PetResponseDto> findPets(User user) {
        return petRepository.findAllByUser(user)
                .stream().map(PetResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PetResponseDto findPet(User user, Long petId) throws Exception {
        Pet pet = findPetById(petId);
        checkPermission(user, pet);

        return new PetResponseDto(pet);
    }

    // TODO : 커스텀 excpetion
    private Pet findPetById(Long id) throws Exception {
        return petRepository.findById(id).orElseThrow(Exception::new);
    }

    // TODO : 권한 exception
    private void checkPermission(User user, Pet pet) throws AuthException {
        if (!user.isPossibleToAccessUserPet(pet.getUser().getId())) {
            throw new AuthException();
        }
    }
}
