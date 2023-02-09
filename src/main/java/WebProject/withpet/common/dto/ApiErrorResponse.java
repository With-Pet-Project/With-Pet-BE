package WebProject.withpet.common.dto;

import WebProject.withpet.common.constants.ErrorCode;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Data
@Builder
public class ApiErrorResponse {

    private int code;
    private String message;

    public static ResponseEntity<ApiErrorResponse> toResponseEntity(ErrorCode e) {
        return ResponseEntity
            .status(e.getHttpStatus())
            .body(
                ApiErrorResponse.builder()
                    .code(e.getHttpStatus().value())
                    .message(e.getMessage())
                    .build());
    }
}
