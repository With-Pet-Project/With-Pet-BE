package WebProject.withpet.common.constants;

import WebProject.withpet.common.dto.ApiResponse;

public class ResponseConstants {

    public static final ApiResponse<Void> RESPONSE_SAVE_OK = new ApiResponse<>(201,
            ResponseMessages.SAVE_MESSAGE.getContent());
    public static final ApiResponse<Void> RESPONSE_VIEW_OK = new ApiResponse<>(200,
            ResponseMessages.VIEW_MESSAGE.getContent());
    public static final ApiResponse<Void> RESPONSE_DEL_OK = new ApiResponse<>(200,
            ResponseMessages.DEL_MESSAGE.getContent());
    public static final ApiResponse<Void> RESPONSE_UPDATE_OK = new ApiResponse<>(200,
            ResponseMessages.UPDATE_MESSAGE.getContent());

}
