package WebProject.withpet.common.dto;

import WebProject.withpet.common.constants.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;

@Getter
@Data
@Builder
public class ApiErrorResponse {

    private int code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomFieldError> customFieldErrors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CustomFieldError> constraintMessages;

    public static ResponseEntity<ApiErrorResponse> toResponseEntity(ErrorCode e) {
        return ResponseEntity
            .status(e.getHttpStatus())
            .body(
                ApiErrorResponse.builder()
                    .code(e.getHttpStatus().value())
                    .message(e.getMessage())
                    .build());
    }

    public static ResponseEntity<ApiErrorResponse> toResponseEntityWithErrors(ErrorCode e,
        Errors errors) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
            .code(e.getHttpStatus().value())
            .message(e.getMessage())
            .build();
        apiErrorResponse.setCustomFieldErrors(errors.getFieldErrors());

        return ResponseEntity
            .status(e.getHttpStatus())
            .body(apiErrorResponse);
    }

    public static ResponseEntity<ApiErrorResponse> toResponseEntityWithRequestParameterException(
        ErrorCode e,
        MissingServletRequestParameterException ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
            .code(e.getHttpStatus().value())
            .message(e.getMessage())
            .build();

        apiErrorResponse.setCustomFieldErrors(ex);

        return ResponseEntity
            .status(e.getHttpStatus())
            .body(apiErrorResponse);

    }

    public static ResponseEntity<ApiErrorResponse> toResponseEntityWithConstraints(ErrorCode e,
        Set<ConstraintViolation<?>> violations
    ) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
            .code(e.getHttpStatus().value())
            .message(e.getMessage())
            .build();
        apiErrorResponse.setConstraintMessages(violations);

        return ResponseEntity
            .status(e.getHttpStatus())
            .body(apiErrorResponse);
    }

    public void setCustomFieldErrors(List<FieldError> fieldErrors) {

        customFieldErrors = new ArrayList<>();

        fieldErrors.forEach(error -> {
            customFieldErrors.add(new CustomFieldError(
                error.getField(),
                error.getRejectedValue(),
                error.getDefaultMessage()
            ));
        });
    }

    public void setCustomFieldErrors(MissingServletRequestParameterException ex) {

        customFieldErrors = new ArrayList<>();

        customFieldErrors.add(CustomFieldError.builder()
            .field(ex.getParameterName())
            .reason(ex.getMessage())
            .build()
        );
    }

    public void setConstraintMessages(Set<ConstraintViolation<?>> violations) {

        this.constraintMessages = violations
            .stream()
            .map(s -> new CustomFieldError(s.getPropertyPath().toString(), s.getInvalidValue(),
                s.getMessage()))
            .collect(Collectors.toList());


    }


    @Builder
    public static class CustomFieldError {

        private String field;
        private Object value;
        private String reason;

        public CustomFieldError(String field, Object value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public String getField() {
            return field;
        }

        public Object getValue() {
            return value;
        }

        public String getReason() {
            return reason;
        }
    }
}
