package WebProject.withpet.pets.controller;

import WebProject.withpet.auth.PrincipalDetails;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.dto.ApiResponse;
import WebProject.withpet.pets.dto.HealthCareRequestDto;
import WebProject.withpet.pets.dto.HealthCareResponseDto;
import WebProject.withpet.pets.service.HealthCareService;
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
@RequestMapping("/pet/{petId}/health")
public class HealthCareController {
    private final HealthCareService healthCareService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerHealthCare(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long petId,
            @Valid @RequestBody HealthCareRequestDto request) {
        healthCareService.registerHealthCare(petId, principalDetails.getUser(), request);
        return ResponseEntity.ok(ResponseConstants.RESPONSE_SAVE_OK);
    }

    @PutMapping("/{healthCareId}")
    public ResponseEntity<ApiResponse<Void>> updateHealthCare(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long petId,
            @PathVariable Long healthCareId, @Valid @RequestBody HealthCareRequestDto request) {
        healthCareService.updateHealthCare(healthCareId, petId, principalDetails.getUser(), request);
        return ResponseEntity.ok(ResponseConstants.RESPONSE_UPDATE_OK);
    }

    @DeleteMapping("/{healthCareId}")
    public ResponseEntity<ApiResponse<Void>> deleteHealthCare(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long petId,
            @PathVariable Long healthCareId) {
        healthCareService.deleteHealthCare(healthCareId, petId, principalDetails.getUser());
        return ResponseEntity.ok(ResponseConstants.RESPONSE_DEL_OK);
    }

    @GetMapping("/monthly")
    public ResponseEntity<ApiResponse<List<HealthCareResponseDto>>> getMonthlyHealthCares(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long petId,
            @RequestParam int year, @RequestParam int month) {
        List<HealthCareResponseDto> healthCares = healthCareService.getMonthlyHealthCares(petId,
                principalDetails.getUser(), year, month);

        ApiResponse<List<HealthCareResponseDto>> response = new ApiResponse<List<HealthCareResponseDto>>(200,
                "정상적으로 조회되었습니다.", healthCares);
        return ResponseEntity.ok(response);
    }

}
