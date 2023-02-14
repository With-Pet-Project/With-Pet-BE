package WebProject.withpet.pets.service;

import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.UnauthorizedException;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.pets.domain.PetRepository;
import WebProject.withpet.pets.dto.PetRequestDto;
import WebProject.withpet.pets.dto.PetResponseDto;
import WebProject.withpet.users.domain.User;
import java.util.List;
import java.util.stream.Collectors;
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
    public void updatePet(Long petId, User user, PetRequestDto petRequestDto) {
        Pet pet = findPetById(petId);
        checkPermission(user, pet);

        Pet updatePet = petRequestDto.toEntity(user);
        pet.update(updatePet);
    }

    @Transactional
    public void deletePet(Long petId, User user) {
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
    public PetResponseDto findPet(User user, Long petId) {
        Pet pet = findPetById(petId);
        checkPermission(user, pet);

        return new PetResponseDto(pet);
    }

    private Pet findPetById(Long id) throws DataNotFoundException {
        return petRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    private void checkPermission(User user, Pet pet) throws UnauthorizedException {
        if (!Objects.equals(pet.getUser().getId(), user.getId())) {
            throw new UnauthorizedException();
        }
    }
}
