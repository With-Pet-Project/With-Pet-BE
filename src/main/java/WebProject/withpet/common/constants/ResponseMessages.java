package WebProject.withpet.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessages {
    SAVE_MESSAGE("정상적으로 저장되었습니다."),
    VIEW_MESSAGE("정상적으로 조회되었습니다."),
    UPDATE_MESSAGE("정상적으로 수정되었습니다."),
    DEL_MESSAGE("정상적으로 삭제되었습니다.");

    private final String content;
}
