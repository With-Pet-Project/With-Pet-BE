package WebProject.withpet.auth.service;

import WebProject.withpet.auth.domain.ConfirmationToken;
import WebProject.withpet.auth.domain.ConfirmationTokenRepository;
import java.time.LocalDateTime;
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
}
