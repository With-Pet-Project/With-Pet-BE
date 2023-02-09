package WebProject.withpet.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보가 존재하지 않습니다."),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 데이터가 존재하지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
