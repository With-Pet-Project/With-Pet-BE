package WebProject.withpet.pets.service;

import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.pets.domain.Consumption;
import WebProject.withpet.pets.domain.ConsumptionRepository;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.pets.domain.PetRepository;
import WebProject.withpet.pets.dto.ConsumptionRequestDto;
import WebProject.withpet.pets.dto.ConsumptionResponseDto;
import WebProject.withpet.pets.dto.MonthlyConsumptionByPetResponseDto;
import WebProject.withpet.pets.dto.MonthlyConsumptionByUserResponseDto;
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
    private final PetRepository petRepository;

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
    public List<MonthlyConsumptionByUserResponseDto> getMonthlyConsumptionsByUser(User user, int year, int month) {
        return consumptionRepository.getDailyConsumptionsByUser(user, year, month);
    }

    private Consumption findConsumptionById(Long id) {
        return consumptionRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    public Consumption accessConsumption(Long petId, Long consumptionId, User user) {
        petService.accessPet(user, petId);
        return findConsumptionById(consumptionId);
    }
}
