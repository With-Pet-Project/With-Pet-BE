package WebProject.withpet.pets.service;

import WebProject.withpet.common.constants.ErrorCode;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.DuplicateDateException;
import WebProject.withpet.pets.domain.Consumption;
import WebProject.withpet.pets.domain.ConsumptionRepository;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.pets.dto.ConsumptionRequestDto;
import WebProject.withpet.pets.dto.ConsumptionResponseDto;
import WebProject.withpet.pets.dto.MonthlyConsumptionByPetResponseDto;
import WebProject.withpet.users.domain.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsumptionService {
    private final ConsumptionRepository consumptionRepository;
    private final PetService petService;

    @Transactional
    public void saveConsumption(Long petId, User user, ConsumptionRequestDto request) {
        Pet pet = petService.accessPet(user, petId);
        if (isDuplicateConsumption(pet, request)) {
            throw new DuplicateDateException(ErrorCode.DUPLICATE_DATE);
        }
        consumptionRepository.save(request.toEntity(user, pet));
    }

    @Transactional
    public void updateConsumption(Long consumptionId, Long petId, User user, ConsumptionRequestDto request) {
        Pet pet = petService.accessPet(user, petId);
        Consumption consumption = findConsumptionById(consumptionId);
        consumption.update(request.toEntity(user, pet));
    }

    @Transactional
    public void deleteConsumption(Long consumptionId, Long petId, User user) {
        Consumption consumption = accessConsumption(petId, consumptionId, user);
        consumptionRepository.delete(consumption);
    }

    @Transactional(readOnly = true)
    public List<MonthlyConsumptionByPetResponseDto> getMonthlyConsumptionsByPet(Long petId, User user, int year,
                                                                                int month) {
        Pet pet = petService.accessPet(user, petId);
        List<ConsumptionResponseDto> consumptions = consumptionRepository.findMonthlyConsumptionsByPet(pet, year,
                month);

        List<MonthlyConsumptionByPetResponseDto> response = new ArrayList<>();
        consumptions.forEach(consumption -> response.add(new MonthlyConsumptionByPetResponseDto(consumption)));
        return response;
    }

    @Transactional(readOnly = true)
    public List<ConsumptionResponseDto> getMonthlyConsumptionsByUser(User user, int year, int month) {
        return consumptionRepository.findMonthlyConsumptionsByUser(user, year, month);
    }

    private Consumption findConsumptionById(Long id) {
        return consumptionRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    public Consumption accessConsumption(Long petId, Long consumptionId, User user) {
        petService.accessPet(user, petId);
        return findConsumptionById(consumptionId);
    }

    private boolean isDuplicateConsumption(Pet pet, ConsumptionRequestDto requestDto) {
        return consumptionRepository.isDuplicateDateConsumption(pet, requestDto.getYear(), requestDto.getMonth(),
                requestDto.getWeek(), requestDto.getDay());
    }
}
