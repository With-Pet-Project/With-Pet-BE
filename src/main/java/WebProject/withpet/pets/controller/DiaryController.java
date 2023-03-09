package WebProject.withpet.pets.controller;

import WebProject.withpet.auth.PrincipalDetails;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.dto.ApiResponse;
import WebProject.withpet.pets.dto.DiaryRequestDto;
import WebProject.withpet.pets.dto.DiaryUpdateDto;
import WebProject.withpet.pets.service.DiaryService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet/{petId}/diary")
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerDiary(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                           @Valid @RequestBody DiaryRequestDto requestDto,
                                                           @PathVariable Long petId) {
        diaryService.registerDiary(petId, principalDetails.getUser(), requestDto);
        return ResponseEntity.ok(ResponseConstants.RESPONSE_SAVE_OK);
    }

    @PutMapping("/{diaryId}")
    public ResponseEntity<ApiResponse<Void>> updateDiary(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                         @Valid @RequestBody DiaryUpdateDto requestDto,
                                                         @PathVariable Long petId, @PathVariable Long diaryId) {
        diaryService.updateDiary(diaryId, petId, principalDetails.getUser(), requestDto);
        return ResponseEntity.ok(ResponseConstants.RESPONSE_UPDATE_OK);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<ApiResponse<Void>> deleteDiary(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                         @Valid @RequestBody DiaryUpdateDto requestDto,
                                                         @PathVariable Long petId, @PathVariable Long diaryId) {
        diaryService.deleteDiary(diaryId, petId, principalDetails.getUser());
        return ResponseEntity.ok(ResponseConstants.RESPONSE_DEL_OK);
    }

}
