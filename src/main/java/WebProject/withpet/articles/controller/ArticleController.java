package WebProject.withpet.articles.controller;


import WebProject.withpet.articles.domain.Filter;
import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.articles.dto.ArticleUpdateRequestDto;
import WebProject.withpet.articles.dto.ViewArticleListRequestDto;
import WebProject.withpet.articles.dto.ViewArticleListResponseDto;
import WebProject.withpet.articles.dto.ViewSpecificArticleResponseDto;
import WebProject.withpet.articles.service.ArticleService;
import WebProject.withpet.articles.dto.ArticleCreateRequestDto;
import WebProject.withpet.articles.validation.ArticleCreateValidator;
import WebProject.withpet.articles.validation.ArticleScrollDownValidator;
import WebProject.withpet.common.auth.PrincipalDetails;
import WebProject.withpet.common.constants.ErrorCode;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.constants.ResponseMessages;
import WebProject.withpet.common.dto.ApiResponse;
import WebProject.withpet.common.exception.ArticleException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    private final ArticleCreateValidator articleCreateValidator;

    private final ArticleScrollDownValidator articleScrollDownValidator;

    @PostMapping("/article")
    public ResponseEntity<ApiResponse<Void>> createArticle(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody ArticleCreateRequestDto request) {

        Errors errors = new BeanPropertyBindingResult(request, "articleCreateRequestDto");

        //커스텀 검증
        articleCreateValidator.validate(request, errors);

        if (errors.hasErrors()) {
            throw new ArticleException(ErrorCode.INVALID_PARAMETER, errors);
        } else {
            articleService.createArticle(principalDetails.getUser(), request);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseConstants.RESPONSE_SAVE_OK);
    }

    @GetMapping("/articles/{articleId}")
    public ResponseEntity<ApiResponse<ViewSpecificArticleResponseDto>> viewSpecificArticle(
        @PathVariable("articleId") @NotNull(message = "게시글 id를 Url에 담아줘야 합니다.") Long articleId) {

        ApiResponse response = new ApiResponse<>(200, ResponseMessages.VIEW_MESSAGE.getContent(),
            articleService.viewSpecificArticle(articleId));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PatchMapping("/article/{articleId}")
    public ResponseEntity<ApiResponse<Void>> updateArticle(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("articleId") Long articleId,
        @RequestBody ArticleUpdateRequestDto dto) {

        articleService.updateArticle(principalDetails.getUser(), articleId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstants.RESPONSE_UPDATE_OK);
    }

    @GetMapping("/articles")
    public ResponseEntity<ApiResponse<ViewArticleListResponseDto>> scrollDownArticle(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestParam(name = "tag", required = false) Tag tag,
        @RequestParam(name = "filter") Filter filter,
        @RequestParam(name = "place1", required = false) String place1,
        @RequestParam(name = "place2", required = false) String place2,
        @RequestParam(name = "lastArticleId") Long lastArticleId,
        @RequestParam(name = "size") Integer size,
        @RequestParam(name = "param", required = false) String param) {

        ViewArticleListRequestDto dto = ViewArticleListRequestDto.builder()
            .tag(tag)
            .filter(filter)
            .place1(place1)
            .place2(place2)
            .lastArticleId(lastArticleId)
            .size(size)
            .param(param)
            .build();

        Errors errors = new BeanPropertyBindingResult(dto, "ViewArticleListRequestDto");
        articleScrollDownValidator.validate(dto, errors);

        if (errors.hasErrors()) {
            throw new ArticleException(ErrorCode.INVALID_PARAMETER, errors);
        } else {
            ApiResponse<ViewArticleListResponseDto> response = new ApiResponse<>(
                200, ResponseMessages.VIEW_MESSAGE.getContent(),
                articleService.scrollDownArticle(principalDetails.getUser(),dto));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }

    @DeleteMapping("/article/{articleId}")
    public ResponseEntity<ApiResponse<Void>> deleteArticle(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("articleId") Long articleId) {

        articleService.deleteArticle(principalDetails.getUser(), articleId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstants.RESPONSE_DEL_OK);
    }

}
