package WebProject.withpet.pets.controller;

import WebProject.withpet.common.auth.PrincipalDetails;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.constants.ResponseMessages;
import WebProject.withpet.common.dto.ApiResponse;
import WebProject.withpet.pets.dto.ConsumptionRequestDto;
import WebProject.withpet.pets.dto.MonthlyConsumptionByPetResponseDto;
import WebProject.withpet.pets.dto.MonthlyConsumptionsByUserResponseDto;
import WebProject.withpet.pets.service.ConsumptionService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConsumptionController {
    private final ConsumptionService consumptionService;

    @PostMapping("/pet/{petId}/consumption")
    public ResponseEntity<ApiResponse<Void>> saveConsumption(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                             @Valid @RequestBody ConsumptionRequestDto request,
                                                             @PathVariable Long petId) {
        consumptionService.saveConsumption(petId, principalDetails.getUser(), request);
        return ResponseEntity.ok(ResponseConstants.RESPONSE_SAVE_OK);
    }

    @PutMapping("/pet/{petId}/consumption/{consumptionId}")
    public ResponseEntity<ApiResponse<Void>> updateConsumption(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody ConsumptionRequestDto request, @PathVariable Long petId,
            @PathVariable Long consumptionId) {
        consumptionService.updateConsumption(consumptionId, petId, principalDetails.getUser(), request);
        return ResponseEntity.ok(ResponseConstants.RESPONSE_UPDATE_OK);
    }

    @DeleteMapping("/pet/{petId}/consumption/{consumptionId}")
    public ResponseEntity<ApiResponse<Void>> deleteConsumption(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long petId,
            @PathVariable Long consumptionId) {
        consumptionService.deleteConsumption(consumptionId, petId, principalDetails.getUser());
        return ResponseEntity.ok(ResponseConstants.RESPONSE_DEL_OK);
    }

    @GetMapping("/pet/{petId}/consumption")
    public ResponseEntity<ApiResponse<List<MonthlyConsumptionByPetResponseDto>>> getMonthlyConsumptionsByPet(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long petId,
            @RequestParam int year, @RequestParam int month) {
        ApiResponse<List<MonthlyConsumptionByPetResponseDto>> response = new ApiResponse<>(200,
                ResponseMessages.VIEW_MESSAGE.getContent(),
                consumptionService.getMonthlyConsumptionsByPet(petId, principalDetails.getUser(), year, month));
        return ResponseEntity.ok(response);
    }


    @GetMapping("/pet/consumption")
    public ResponseEntity<ApiResponse<List<MonthlyConsumptionsByUserResponseDto>>> getMonthlyConsumptionsByUser(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam int year, @RequestParam int month
    ) {
        List<MonthlyConsumptionsByUserResponseDto> consumptions = consumptionService.getMonthlyConsumptionsByUser(
                principalDetails.getUser(), year, month);
        ApiResponse<List<MonthlyConsumptionsByUserResponseDto>> response = new ApiResponse<>(
                200, ResponseMessages.VIEW_MESSAGE.getContent(), consumptions
        );
        return ResponseEntity.ok(response);
    }
}
