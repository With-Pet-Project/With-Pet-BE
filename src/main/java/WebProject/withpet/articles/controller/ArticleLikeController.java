package WebProject.withpet.articles.controller;


import WebProject.withpet.articles.dto.ArticleLikeRequestDto;
import WebProject.withpet.articles.service.ArticleLikeService;
import WebProject.withpet.auth.PrincipalDetails;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article_like")
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;


    @PostMapping()
    public ResponseEntity<ApiResponse<Void>> addArticleLike(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody ArticleLikeRequestDto dto) {

        articleLikeService.addArticleLike(principalDetails.getUser(), dto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstants.RESPONSE_SAVE_OK);

    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<Void>> cancelArticleLike(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody ArticleLikeRequestDto dto) {

        articleLikeService.cancelArticleLike(principalDetails.getUser(), dto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstants.RESPONSE_DEL_OK);
    }
}
