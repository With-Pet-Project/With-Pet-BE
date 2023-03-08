package WebProject.withpet.users.controller;

import WebProject.withpet.auth.PrincipalDetails;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.constants.ResponseMessages;
import WebProject.withpet.common.dto.ApiResponse;
import WebProject.withpet.users.dto.MypageChangeRequestDto;
import WebProject.withpet.users.dto.ViewMypageResponseDto;
import WebProject.withpet.users.service.MypageService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;

    @PatchMapping()
    public ResponseEntity<ApiResponse<Void>> changeUserInfo(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestPart(required = false) String nickName,
        @RequestPart(required = false) List<MultipartFile> images) {

        mypageService.changeUserInfo(principalDetails.getUser(), nickName, images);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstants.RESPONSE_UPDATE_OK);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<ViewMypageResponseDto>> viewMypage(
        @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ApiResponse<ViewMypageResponseDto> response = new ApiResponse<>(200,
            ResponseMessages.VIEW_MESSAGE.getContent(),
            mypageService.viewMypage(principalDetails.getUser()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
