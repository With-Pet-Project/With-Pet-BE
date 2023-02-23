package WebProject.withpet.common.exception;

import WebProject.withpet.common.constants.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
@AllArgsConstructor
public class ArticleCreateException extends RuntimeException{

    ErrorCode errorCode;

    Errors errors;
}
