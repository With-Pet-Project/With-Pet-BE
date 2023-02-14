package WebProject.withpet.challenge.controller;

import WebProject.withpet.challenge.dto.ChallengeRequestDto;
import WebProject.withpet.challenge.service.ChallengeService;
import WebProject.withpet.common.auth.PrincipalDetails;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.dto.ApiResponse;
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
@RequestMapping("/challenge")
public class ChallengeController {
    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerChallenge(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody @Valid ChallengeRequestDto requestDto
    ) {
        challengeService.registerChallenge(principalDetails.getUser(), requestDto);
        return ResponseEntity.ok(ResponseConstants.RESPONSE_SAVE_OK);
    }

    @PutMapping("/{challengeId}")
    public ResponseEntity<ApiResponse<Void>> updateChallenge(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody @Valid ChallengeRequestDto requestDto,
            @PathVariable Long challengeId
    ) {
        challengeService.updateChallenge(challengeId, principalDetails.getUser(), requestDto);
        return ResponseEntity.ok(ResponseConstants.RESPONSE_UPDATE_OK);
    }

    @DeleteMapping("/{challengeId}")
    public ResponseEntity<ApiResponse<Void>> deleteChallenge(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long challengeId
    ) {
        challengeService.deleteChallenge(challengeId, principalDetails.getUser());
        return ResponseEntity.ok(ResponseConstants.RESPONSE_DEL_OK);
    }
}
