package WebProject.withpet.pets.service;

import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.pets.domain.Consumption;
import WebProject.withpet.pets.domain.ConsumptionRepository;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.pets.dto.ConsumptionRequestDto;
import WebProject.withpet.pets.dto.ConsumptionResponseDto;
import WebProject.withpet.users.domain.User;
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
    public List<ConsumptionResponseDto> getMonthlyConsumptions(Long petId, User user, int year, int month) {
        Pet pet = petService.accessPet(user, petId);
        return consumptionRepository.findMonthlyConsumptions(pet, year, month);
    }

    private Consumption findConsumptionById(Long id) {
        return consumptionRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    public Consumption accessConsumption(Long petId, Long consumptionId, User user) {
        petService.accessPet(user, petId);
        return findConsumptionById(consumptionId);
    }
}
