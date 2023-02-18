package WebProject.withpet.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final int code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
