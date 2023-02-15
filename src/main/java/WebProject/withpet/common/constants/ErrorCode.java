package WebProject.withpet.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보가 존재하지 않습니다."),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 데이터가 존재하지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다."),
    KAKAO_JSON_OBJECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 로그인 시 JsonObject 에러 발생"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "유효하지 않은 데이터가 존재합니다."),
    DUPLICATE_NICK_NAME(HttpStatus.BAD_REQUEST, "해당 닉네임은 이미 존재합니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "해당 이메일은 이미 존재합니다");

    private final HttpStatus httpStatus;
    private final String message;
}
