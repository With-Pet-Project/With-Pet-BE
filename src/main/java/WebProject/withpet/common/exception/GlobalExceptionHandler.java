package WebProject.withpet.common.exception;

import WebProject.withpet.common.constants.ErrorCode;
import WebProject.withpet.common.dto.ApiErrorResponse;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<ApiErrorResponse> handleDataNotFoundException(
        DataNotFoundException e) {
        log.error("handleDataNotFoundException", e);

        return ApiErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ApiErrorResponse> handleUnauthorizedException(
        UnauthorizedException e) {
        log.error("handleUnauthorizedException", e);

        return ApiErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ApiErrorResponse> handleUserNotFoundException(
        UserNotFoundException e) {
        log.error("handleUserNotFoundException", e);

        return ApiErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(JSONException.class)
    public ResponseEntity<ApiErrorResponse> handleJsonException(JSONException e) {

        log.error("handleJsonException", e);

        return ApiErrorResponse.toResponseEntity(ErrorCode.KAKAO_JSON_OBJECT_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        return ApiErrorResponse.toResponseEntityWithErrors(ErrorCode.INVALID_PARAMETER,
            e.getBindingResult());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(
        ConstraintViolationException e) {
        log.error("handleConstraintViolationException", e);
        return ApiErrorResponse.toResponseEntityWithConstraints(ErrorCode.INVALID_PARAMETER,
            e.getConstraintViolations());
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateException(DuplicateException e) {
        log.error("handleDuplicateException", e);
        return ApiErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameterException(
        MissingServletRequestParameterException e) {
        log.error("handleMissingServletRequestParameterException", e);
        return ApiErrorResponse.toResponseEntityWithRequestParameterException(
            ErrorCode.INVALID_PARAMETER, e);
    }

    @ExceptionHandler(ArticleException.class)
    public ResponseEntity<ApiErrorResponse> handleArticleCreateException(ArticleException e) {
        log.error("handleArticleCreateException", e);
        return ApiErrorResponse.toResponseEntityWithErrors(e.getErrorCode(),
            e.getErrors());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException", e);
        return ApiErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
