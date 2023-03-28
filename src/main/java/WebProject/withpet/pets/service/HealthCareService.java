package WebProject.withpet.pets.service;

import WebProject.withpet.common.constants.ErrorCode;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.DuplicateDateException;
import WebProject.withpet.pets.domain.HealthCare;
import WebProject.withpet.pets.domain.HealthCareRepository;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.pets.dto.HealthCareRequestDto;
import WebProject.withpet.pets.dto.HealthCareResponseDto;
import WebProject.withpet.users.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HealthCareService {
    private final HealthCareRepository healthCareRepository;
    private final PetService petService;

    @Transactional
    public void registerHealthCare(Long petId, User user, HealthCareRequestDto request) {
        Pet pet = petService.accessPet(user, petId);
        if (isDuplicateHealthCare(pet, request)) {
            throw new DuplicateDateException(ErrorCode.DUPLICATE_DATE);
        }
        healthCareRepository.save(request.toEntity(pet));
    }

    @Transactional
    public void updateHealthCare(Long healthCareId, Long petId, User user, HealthCareRequestDto request) {
        Pet pet = petService.accessPet(user, petId);
        findHealthCareById(healthCareId).update(request.toEntity(pet));
    }

    @Transactional
    public void deleteHealthCare(Long healthCareId, Long petId, User user) {
        Pet pet = petService.accessPet(user, petId);
        healthCareRepository.delete(findHealthCareById(healthCareId));
    }

    @Transactional(readOnly = true)
    public List<HealthCareResponseDto> getMonthlyHealthCares(Long petId, User user, int year, int month) {
        Pet pet = petService.accessPet(user, petId);
        return healthCareRepository.findMonthlyHealthCares(pet, year, month);
    }

    private HealthCare findHealthCareById(Long id) {
        return healthCareRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    private boolean isDuplicateHealthCare(Pet pet, HealthCareRequestDto requestDto) {
        return healthCareRepository.isDuplicateDateHealthCare(pet, requestDto.getYear(), requestDto.getMonth(),
                requestDto.getWeek(), requestDto.getDay());
    }
}
