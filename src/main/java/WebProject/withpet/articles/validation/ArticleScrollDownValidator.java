package WebProject.withpet.articles.validation;

import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.articles.dto.ViewArticleListRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ArticleScrollDownValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ViewArticleListRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ViewArticleListRequestDto request = (ViewArticleListRequestDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "filter", "INVALID_PARAMETER",
            "필터는 필수 값입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastArticleId", "INVALID_PARAMETER",
            "마지막 조회 게시글 id값은 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tag", "INVALID_PARAMETER",
            "조회할 게시글 갯수는 필수입니다.");

        if (request.getTag().equals(Tag.GOODS) || request.getTag().equals(Tag.PLACE)
            || request.getTag().equals(Tag.ETC)) {
            if (request.getPlace1() != null || request.getPlace2() != null) {
                errors.reject("INVALID_PARAMETER", "해당 태그에서는 지역이 존재하지 않습니다.");
            }
        }
    }
}
