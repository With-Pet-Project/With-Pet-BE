package WebProject.withpet.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenResponseDto {
    private final String refreshToken;
    private final String accessToken;
}
