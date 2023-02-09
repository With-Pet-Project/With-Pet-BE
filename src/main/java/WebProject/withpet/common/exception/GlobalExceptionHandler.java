package WebProject.withpet.common.exception;

import WebProject.withpet.common.constants.ErrorCode;
import WebProject.withpet.common.dto.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<ApiErrorResponse> handleDataNotFoundException(
            DataNotFoundException e) {
        logger.error("handleDataNotFoundException", e);

        return ApiErrorResponse.toResponseEntity(ErrorCode.DATA_NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ApiErrorResponse> handleUnauthorizedException(
            UnauthorizedException e) {
        logger.error("handleUnauthorizedException", e);

        return ApiErrorResponse.toResponseEntity(ErrorCode.UNAUTHORIZED);
    }

}
