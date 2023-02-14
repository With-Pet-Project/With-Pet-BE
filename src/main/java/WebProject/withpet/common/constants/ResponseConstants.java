package WebProject.withpet.common.constants;

import WebProject.withpet.common.dto.ApiResponse;

public class ResponseConstants {

    public static final ApiResponse<Void> RESPONSE_SAVE_OK = new ApiResponse<>(201,
        "정상적으로 저장되었습니다.");
    public static final ApiResponse<Void> RESPONSE_VIEW_OK = new ApiResponse<>(200,
        "정상적으로 조회되었습니다.");
    public static final ApiResponse<Void> RESPONSE_DEL_OK = new ApiResponse<>(200,
        "정상적으로 삭제되었습니다.");
    public static final ApiResponse<Void> RESPONSE_UPDATE_OK = new ApiResponse<>(200,
        "정상적으로 수정되었습니다.");

    public static final ApiResponse<Void> DUPLICATE_CHECK_OK = new ApiResponse<>(200,
        "사용 가능한 닉네임입니다");
}
