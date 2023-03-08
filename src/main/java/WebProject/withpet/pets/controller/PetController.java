package WebProject.withpet.pets.controller;

import WebProject.withpet.auth.PrincipalDetails;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.constants.ResponseMessages;
import WebProject.withpet.common.dto.ApiResponse;
import WebProject.withpet.pets.dto.PetRequestDto;
import WebProject.withpet.pets.dto.PetResponseDto;
import WebProject.withpet.pets.service.PetService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerPet(@AuthenticationPrincipal
                                                         PrincipalDetails principalDetails,
                                                         @Valid @RequestBody PetRequestDto pet) {
        petService.registerPet(pet, principalDetails.getUser());
        return ResponseEntity.ok(ResponseConstants.RESPONSE_SAVE_OK);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<ApiResponse<PetResponseDto>> findPet(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long petId) throws Exception {
        PetResponseDto pet = petService.findPet(principalDetails.getUser(), petId);
        ApiResponse<PetResponseDto> apiResponse = new ApiResponse<>(200, ResponseMessages.VIEW_MESSAGE.getContent(),
                pet);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{petId}")
    public ResponseEntity<ApiResponse<Void>> updatePet(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                       @PathVariable Long petId,
                                                       @Valid @RequestBody PetRequestDto petRequestDto)
            throws Exception {
        petService.updatePet(petId, principalDetails.getUser(), petRequestDto);
        return ResponseEntity.ok(ResponseConstants.RESPONSE_UPDATE_OK);
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<ApiResponse<Void>> deletePet(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                       @PathVariable Long petId) throws Exception {
        petService.deletePet(petId, principalDetails.getUser());
        return ResponseEntity.ok(ResponseConstants.RESPONSE_DEL_OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PetResponseDto>>> findPets(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<PetResponseDto> pets = petService.findPets(principalDetails.getUser());
        ApiResponse<List<PetResponseDto>> apiResponse = new ApiResponse<>(
                200, ResponseMessages.VIEW_MESSAGE.getContent(), pets
        );
        return ResponseEntity.ok(apiResponse);
    }
}
