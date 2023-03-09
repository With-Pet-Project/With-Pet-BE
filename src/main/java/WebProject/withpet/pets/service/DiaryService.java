package WebProject.withpet.pets.service;

import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.pets.domain.Diary;
import WebProject.withpet.pets.domain.DiaryRepository;
import WebProject.withpet.pets.domain.Pet;
import WebProject.withpet.pets.dto.DiaryRequestDto;
import WebProject.withpet.pets.dto.DiaryUpdateDto;
import WebProject.withpet.users.domain.User;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final PetService petService;

    @Transactional
    public void registerDiary(Long petId, User user, DiaryRequestDto requestDto) {
        Pet pet = petService.accessPet(user, petId);
        diaryRepository.save(requestDto.toEntity(pet));
    }

    @Transactional
    public void updateDiary(Long diaryId, Long petId, User user, DiaryUpdateDto requestDto) {
        if (!Objects.equals(petId, requestDto.getPetId())) {
            petService.accessPet(user, petId); // url의 pet 접근권한 확인
        }
        Pet pet = petService.accessPet(user, requestDto.getPetId()); // dto의 pet 접근권한 확인
        Diary diary = findDiaryById(diaryId);
        diary.update(requestDto.toEntity(pet));
    }

    @Transactional
    public void deleteDiary(Long diaryId, Long petId, User user) {
        petService.accessPet(user, petId);
        diaryRepository.delete(findDiaryById(diaryId));
    }

    private Diary findDiaryById(Long id) {
        return diaryRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }
}
