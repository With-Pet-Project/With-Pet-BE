package WebProject.withpet.pets.service;

import WebProject.withpet.common.constants.ErrorCode;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.DuplicateException;
import WebProject.withpet.common.exception.UnauthorizedException;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.pets.domain.PetRepository;
import WebProject.withpet.pets.dto.PetRequestDto;
import WebProject.withpet.pets.dto.PetResponseDto;
import WebProject.withpet.users.domain.User;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    @Transactional
    public void registerPet(final PetRequestDto petRequest, final User user) {
        validateNameIsNotDuplicated(petRequest.getName());
        petRepository.save(petRequest.toEntity(user));
    }

    @Transactional
    public void updatePet(Long petId, User user, PetRequestDto petRequest) {
        Pet pet = accessPet(user, petId);
        validateNameIsNotDuplicated(petRequest.getName());
        pet.update(petRequest.toEntity(user));
    }

    @Transactional
    public void deletePet(Long petId, User user) {
        Pet pet = accessPet(user, petId);
        petRepository.delete(pet);
    }

    @Transactional(readOnly = true)
    public List<PetResponseDto> findPets(User user) {
        return petRepository.findAllByUser(user)
                .stream().map(PetResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PetResponseDto findPet(User user, Long petId) {
        Pet pet = accessPet(user, petId);
        return new PetResponseDto(pet);
    }

    private void validateNameIsNotDuplicated(String name) {
        if (petRepository.existsByName(name)) {
            throw new DuplicateException(ErrorCode.DUPLICATE_PET);
        }
    }

    private Pet findPetById(Long id) throws DataNotFoundException {
        return petRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    private void checkPermission(User user, Pet pet) throws UnauthorizedException {
        if (!Objects.equals(pet.getUser().getId(), user.getId())) {
            throw new UnauthorizedException();
        }
    }

    public Pet accessPet(User user, Long id) {
        Pet pet = findPetById(id);
        checkPermission(user, pet);
        return pet;
    }
}
