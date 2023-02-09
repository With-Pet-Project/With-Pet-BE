package WebProject.withpet.pets.controller;

import WebProject.withpet.common.auth.PrincipalDetails;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.dto.ApiResponse;
import WebProject.withpet.pets.dto.PetRequestDto;
import WebProject.withpet.pets.dto.PetResponseDto;
import WebProject.withpet.pets.service.PetService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<Void> registerPet(@AuthenticationPrincipal
                                         PrincipalDetails principalDetails, @Valid @RequestBody PetRequestDto pet) {
        petService.registerPet(pet, principalDetails.getUser());
        return ResponseConstants.RESPONSE_SAVE_OK;
    }

    @GetMapping("/{petId}")
    public ApiResponse<PetResponseDto> findPet(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                               @PathVariable Long petId) throws Exception {
        PetResponseDto pet = petService.findPet(principalDetails.getUser(), petId);
        return new ApiResponse<>(200, "정상적으로 조회되었습니다", pet);
    }

    @PutMapping("/{petId}")
    public ApiResponse<Void> updatePet(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                       @PathVariable Long petId, @Valid @RequestBody PetRequestDto petRequestDto)
            throws Exception {
        petService.updatePet(petId, principalDetails.getUser(), petRequestDto);
        return ResponseConstants.RESPONSE_UPDATE_OK;
    }

    @DeleteMapping("/{petId}")
    public ApiResponse<Void> deletePet(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                       @PathVariable Long petId) throws Exception {
        petService.deletePet(petId, principalDetails.getUser());
        return ResponseConstants.RESPONSE_DEL_OK;
    }

    @GetMapping
    public ApiResponse<List<PetResponseDto>> findPets(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<PetResponseDto> pets = petService.findPets(principalDetails.getUser());
        return new ApiResponse<>(
                200, "정상적으로 조회되었습니다.", pets
        );
    }
}
