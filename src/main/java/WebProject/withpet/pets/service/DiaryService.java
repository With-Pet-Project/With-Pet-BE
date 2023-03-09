package WebProject.withpet.pets.service;

import WebProject.withpet.pets.domain.DiaryRepository;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.pets.dto.DiaryRequestDto;
import WebProject.withpet.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final PetService petService;

    public void registerDiary(Long petId, User user, DiaryRequestDto requestDto) {
        Pet pet = petService.accessPet(user, petId);
        diaryRepository.save(requestDto.toEntity(pet));
    }
}
