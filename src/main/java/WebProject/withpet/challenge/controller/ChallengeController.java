package WebProject.withpet.challenge.controller;

import WebProject.withpet.challenge.dto.ChallengeLogRequestDto;
import WebProject.withpet.challenge.dto.ChallengeRequestDto;
import WebProject.withpet.challenge.dto.DailyChallengeResponseDto;
import WebProject.withpet.challenge.dto.WeeklyChallengeResponseDto;
import WebProject.withpet.challenge.service.ChallengeLogService;
import WebProject.withpet.challenge.service.ChallengeService;
import WebProject.withpet.common.auth.PrincipalDetails;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.dto.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{petId}/challenge")
public class ChallengeController {
    private final ChallengeService challengeService;
    private final ChallengeLogService challengeLogService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerChallenge(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody @Valid ChallengeRequestDto requestDto, @PathVariable Long petId) {
        challengeService.registerChallenge(petId, principalDetails.getUser(), requestDto);
        return ResponseEntity.ok(ResponseConstants.RESPONSE_SAVE_OK);
    }

    @PutMapping("/{challengeId}")
    public ResponseEntity<ApiResponse<Void>> updateChallenge(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                             @RequestBody @Valid ChallengeRequestDto requestDto,
                                                             @PathVariable Long challengeId, @PathVariable Long petId) {
        challengeService.updateChallenge(petId, challengeId, principalDetails.getUser(), requestDto);
        return ResponseEntity.ok(ResponseConstants.RESPONSE_UPDATE_OK);
    }

    @DeleteMapping("/{challengeId}")
    public ResponseEntity<ApiResponse<Void>> deleteChallenge(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                             @PathVariable Long challengeId, @PathVariable Long petId) {
        challengeService.deleteChallenge(petId, challengeId, principalDetails.getUser());
        return ResponseEntity.ok(ResponseConstants.RESPONSE_DEL_OK);
    }

    @PostMapping("/{challengeId}/check")
    public ResponseEntity<ApiResponse<Void>> registerChallengeLog(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long challengeId,
            @RequestBody @Valid ChallengeLogRequestDto requestDto, @PathVariable Long petId) {
        challengeLogService.registerChallengeLog(petId, challengeId, principalDetails.getUser(), requestDto);
        return ResponseEntity.ok(ResponseConstants.RESPONSE_SAVE_OK);
    }

    @DeleteMapping("/check/{challengeLogId}")
    public ResponseEntity<ApiResponse<Void>> deleteChallengeLog(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long challengeLogId,
            @PathVariable Long petId) {
        challengeLogService.deleteChallengeLog(petId, challengeLogId, principalDetails.getUser());
        return ResponseEntity.ok(ResponseConstants.RESPONSE_DEL_OK);
    }

    @GetMapping("/daily")
    public ResponseEntity<ApiResponse<List<DailyChallengeResponseDto>>> getDailyChallenges(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long petId,
            @RequestParam int year, @RequestParam int month, @RequestParam int week, @RequestParam int day) {
        List<DailyChallengeResponseDto> result = challengeService.getDailyChallenges(petId, principalDetails.getUser(),
                year, month, week, day);
        ApiResponse<List<DailyChallengeResponseDto>> response = new ApiResponse<>(200, "정상적으로 조회되었습니다", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/weekly")
    public ResponseEntity<ApiResponse<List<WeeklyChallengeResponseDto>>> getWeeklyChallenges(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long petId,
            @RequestParam int year, @RequestParam int month, @RequestParam int week) {
        List<WeeklyChallengeResponseDto> result = challengeService.getWeeklyChallenges(petId,
                principalDetails.getUser(), year, month, week);
        ApiResponse<List<WeeklyChallengeResponseDto>> response = new ApiResponse<>(200, "정상적으로 조회되었습니다", result);
        return ResponseEntity.ok(response);
    }
}

