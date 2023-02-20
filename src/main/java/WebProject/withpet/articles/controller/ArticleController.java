package WebProject.withpet.articles.controller;


import WebProject.withpet.articles.service.ArticleService;
import WebProject.withpet.articles.dto.ArticleCreateRequestDto;
import WebProject.withpet.common.auth.PrincipalDetails;
import WebProject.withpet.articles.Validation.ArticleValidator;
import WebProject.withpet.common.constants.ErrorCode;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.dto.ApiResponse;
import WebProject.withpet.common.exception.ArticleCreateException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    private final ArticleValidator articleValidator;

    @PostMapping()
    public ResponseEntity<ApiResponse<Void>> createArticle(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody ArticleCreateRequestDto articleCreateRequestDto) {

        Errors errors = new BeanPropertyBindingResult(articleCreateRequestDto,
            "articleCreateRequestDto");

        //커스텀 검증
        articleValidator.validate(articleCreateRequestDto, errors);

        if (errors.hasErrors()) {
            throw new ArticleCreateException(ErrorCode.INVALID_PARAMETER, errors);
        } else {
            articleService.createArticle(principalDetails.getUser(), articleCreateRequestDto);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseConstants.RESPONSE_SAVE_OK);
    }
}
