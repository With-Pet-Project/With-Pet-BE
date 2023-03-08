package WebProject.withpet.articles.validation;


import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.articles.dto.ArticleCreateRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ArticleCreateValidator implements Validator {


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

        if (Tag.isSpecTag(request.getTag())) {

            if (request.getPlace1() == null) {
                errors.rejectValue("place1", "INVALID_PARAMETER", "해당 태그에서 장소1은 필수 값입니다.");
            }
            if (request.getPlace2() == null) {
                errors.rejectValue("place2", "INVALID_PARAMETER", "해당 태그에서 장소1은 필수 값입니다.");
            }

        }

        if (!Tag.isSpecTag(request.getTag())) {
            if (request.getPlace1() != null) {
                errors.rejectValue("place1", "INVALID_PARAMETER", "해당 태그에서는 지역1이 존재하지 않습니다.");
            }
            if (request.getPlace2() != null) {
                errors.rejectValue("place2", "INVALID_PARAMETER", "해당 태그에서는 지역2가 존재하지 않습니다");
            }
        }


    }
}
