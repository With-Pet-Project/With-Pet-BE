package WebProject.withpet.auth.service;

import WebProject.withpet.auth.domain.ConfirmationToken;
import WebProject.withpet.auth.domain.ConfirmationTokenRepository;
import WebProject.withpet.common.constants.ErrorCode;
import WebProject.withpet.common.exception.CustomException;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.UnauthorizedException;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    // 이미 토큰이 있으면 삭제하고 새로 생성
    @Transactional
    public String createOrChangeConfirmationToken(String email, LocalDateTime requestedAt) {
        ConfirmationToken newConfirmationToken = ConfirmationToken.createConfirmationToken(email, requestedAt);
        confirmationTokenRepository.findByEmail(email).ifPresent(confirmationTokenRepository::delete);
        confirmationTokenRepository.save(newConfirmationToken);
        return newConfirmationToken.getContent();
    }

    @Transactional
    public void isRightKey(String email, String key, LocalDateTime requestedAt) {
        ConfirmationToken token = isValidToken(email, requestedAt);
        if (!Objects.equals(token.getContent(), key)) {
            throw new UnauthorizedException();
        }
        confirmationTokenRepository.delete(token); // 확인된 토큰은 삭제
    }

    private ConfirmationToken isValidToken(String email, LocalDateTime requestedAt) {
        ConfirmationToken token = findTokenByEmail(email); // 있는 토큰인가
        isUnexpiredToken(token, requestedAt);
        return token;
    }

    private void isUnexpiredToken(ConfirmationToken token, LocalDateTime requestedAt) {
        if (token.getExpiredAt().isAfter(requestedAt)) {
            confirmationTokenRepository.delete(token); // 만료됐으면 삭제
            throw new CustomException(ErrorCode.EXPIRED_CONFIRMATION_TOKEN);
        }
    }

    private ConfirmationToken findTokenByEmail(String email) {
        return confirmationTokenRepository.findByEmail(email).orElseThrow(DataNotFoundException::new);
    }


}
