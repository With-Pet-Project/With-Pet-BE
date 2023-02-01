package WebProject.withpet.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //개발자가 에러 추가하는 식으로...

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
