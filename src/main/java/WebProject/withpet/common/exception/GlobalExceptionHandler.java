package WebProject.withpet.common.exception;

import WebProject.withpet.common.constants.ErrorCode;
import WebProject.withpet.common.dto.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<ApiErrorResponse> handleDataNotFoundException(
            DataNotFoundException e) {
        log.error("handleDataNotFoundException", e);

        return ApiErrorResponse.toResponseEntity(ErrorCode.DATA_NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ApiErrorResponse> handleUnauthorizedException(
            UnauthorizedException e) {
        log.error("handleUnauthorizedException", e);

        return ApiErrorResponse.toResponseEntity(ErrorCode.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ApiErrorResponse> handleUserNotFoundException(
            UserNotFoundException e) {
        log.error("handleUserNotFoundException", e);

        return ApiErrorResponse.toResponseEntity(ErrorCode.USER_NOT_FOUND);
    }

    @ExceptionHandler(JSONException.class)
    public ResponseEntity<ApiErrorResponse> handleJsonException(JSONException e) {

        log.error("handleJsonException", e);

        return ApiErrorResponse.toResponseEntity(ErrorCode.KAKAO_JSON_OBJECT_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        return ApiErrorResponse.toResponseEntityWithErrors(ErrorCode.DATA_NOT_GIVEN, e.getBindingResult());
    }
}
