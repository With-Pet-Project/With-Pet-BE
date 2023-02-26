package WebProject.withpet.articles.validation;


import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.articles.dto.ArticleCreateRequestDto;
import WebProject.withpet.common.constants.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ArticleValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return ArticleCreateRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ArticleCreateRequestDto request = (ArticleCreateRequestDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tag", "INVALID_PARAMETER",
            "태그는 필수 값입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "INVALID_PARAMETER",
            "제목은 필수 입력값 입니다");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "detailText", "INVALID_PARAMETER",
            "내용은 필수 입력값 입니다");

        if (request.getTag().equals(Tag.LOST) || request.getTag().equals(Tag.HOSPITAL)
            || request.getTag().equals(Tag.WALK)) {

            if (request.getPlace1() == null) {
                errors.rejectValue("place1", "INVALID_PARAMETER", "해당 태그에서 장소1은 필수 값입니다.");
            }
            if (request.getPlace2() == null) {
                errors.rejectValue("place2", "INVALID_PARAMETER", "해당 태그에서 장소1은 필수 값입니다.");
            }

        }

    }
}
