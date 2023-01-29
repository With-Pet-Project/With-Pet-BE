package WebProject.withpet.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class ApiResponse<T> {
    private final int code;
    private final String message;
    private final T data;
}
