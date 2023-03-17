package WebProject.withpet.auth.service;

import WebProject.withpet.auth.domain.RefreshToken;
import WebProject.withpet.auth.domain.RefreshTokenRepository;
import WebProject.withpet.auth.dto.TokenResponseDto;
import WebProject.withpet.auth.util.JwtUtil;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.InvalidRefreshTokenException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    // 로그인 시에
    @Transactional
    public void createOrChangeRefreshToken(String refreshToken, Long userId) {
        refreshTokenRepository.findByUserId(userId)
                .ifPresentOrElse((tokenEntity) -> tokenEntity.changeToken(refreshToken),
                        () -> refreshTokenRepository.save(new RefreshToken(refreshToken, userId)));
    }

    private boolean isValidRefreshToken(String refreshToken, Long userId) {
        return findTokenByUserId(userId).getContent().equals(refreshToken);
    }

    @Transactional
    public TokenResponseDto reissueToken(String refreshToken, Long userId, Date requestedAt) {
        if (!isValidRefreshToken(refreshToken, userId)) {
            throw new InvalidRefreshTokenException();
        }
        String newAccessToken = jwtUtil.reissueAccessToken(refreshToken, userId);
        String newRefreshToken = reissueRefreshToken(refreshToken, userId, requestedAt);
        return new TokenResponseDto(newRefreshToken, newAccessToken);
    }

    @Transactional
    public String reissueRefreshToken(String refreshToken, Long userId, Date requestedAt) {
        if (jwtUtil.isTokenAboutToExpire(refreshToken, requestedAt)) {
            RefreshToken tokenToExpire = refreshTokenRepository.findByUserId(userId).get();
            refreshTokenRepository.delete(tokenToExpire); // 이전에 저장된 리프레시 토큰 지우기
            String newToken = jwtUtil.reissueRefreshToken(userId);
            refreshTokenRepository.save(new RefreshToken(newToken, userId));
            return newToken;
        }
        return null;
    }

    private RefreshToken findTokenByUserId(Long userId) {
        return refreshTokenRepository.findByUserId(userId).orElseThrow(DataNotFoundException::new);
    }
}
