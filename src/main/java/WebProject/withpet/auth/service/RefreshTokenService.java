package WebProject.withpet.auth.service;

import WebProject.withpet.auth.domain.RefreshToken;
import WebProject.withpet.auth.domain.RefreshTokenRepository;
import WebProject.withpet.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void createOrChangeRefreshToken(String refreshToken, Long userId) {
        refreshTokenRepository.findByUserId(userId)
                .ifPresentOrElse((tokenEntity) -> tokenEntity.changeToken(refreshToken),
                        () -> refreshTokenRepository.save(new RefreshToken(refreshToken, userId)));
    }

    public boolean isValidToken(String token, Long userId) {
        RefreshToken refreshToken = findTokenByUserId(userId);
        return refreshToken.getContent().equals(token);
    }

    private RefreshToken findTokenByUserId(Long userId) {
        return refreshTokenRepository.findByUserId(userId).orElseThrow(DataNotFoundException::new);
    }
}
