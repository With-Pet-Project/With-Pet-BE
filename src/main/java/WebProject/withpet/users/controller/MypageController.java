package WebProject.withpet.users.controller;

import WebProject.withpet.common.auth.PrincipalDetails;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.dto.ApiResponse;
import WebProject.withpet.users.dto.MypageChangeRequestDto;
import WebProject.withpet.users.service.MypageService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @PatchMapping ("/mypage")
    public ResponseEntity<ApiResponse<Void>> changeUserInfo(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @Valid @RequestBody MypageChangeRequestDto mypageChangeRequestDto) {

        mypageService.changeUserInfo(principalDetails.getUser(), mypageChangeRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstants.RESPONSE_UPDATE_OK);
    }
}
