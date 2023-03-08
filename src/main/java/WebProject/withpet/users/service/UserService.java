package WebProject.withpet.users.service;

import WebProject.withpet.articles.repository.ArticleRepository;
import WebProject.withpet.auth.PrincipalDetails;
import WebProject.withpet.auth.application.JwtTokenProvider;
import WebProject.withpet.auth.dto.TokenResponseDto;
import WebProject.withpet.auth.service.ConfirmationTokenService;
import WebProject.withpet.auth.service.RefreshTokenService;
import WebProject.withpet.common.constants.ErrorCode;
import WebProject.withpet.common.exception.DuplicateException;
import WebProject.withpet.common.exception.UserNotFoundException;
import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.dto.ChangePasswordDto;
import WebProject.withpet.users.dto.SocialLoginResponseDto;
import WebProject.withpet.users.dto.SocialUserInfoDto;
import WebProject.withpet.users.dto.UserRequestDto;
import WebProject.withpet.users.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserSocialService userSocialService;

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final ConfirmationTokenService confirmationTokenService;

    private final ArticleRepository articleRepository;

    public void register(@Valid UserRequestDto userRequestDto) {
        validateDuplicateEmail(userRequestDto.getEmail());
        validateDuplicateNickname(userRequestDto.getNickname());

        User user = User.builder().nickName(userRequestDto.getNickname()).email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword())).build();

        userRepository.save(user);
    }

    @Transactional
    public SocialLoginResponseDto socialLogin(String code) throws JSONException {

        String accessToken = userSocialService.getAccessToken(code);
        SocialUserInfoDto userInfoByToken = userSocialService.getUserInfoByToken(accessToken);

        String createdToken;

        if (userRepository.findByEmail(userInfoByToken.getEmail()).isEmpty()) {

            //회원가입 진행 후 토큰 생성
            userRepository.save(User.builder().email(userInfoByToken.getEmail()).password("")
                    .nickName(userInfoByToken.getNickname()).build());

            createdToken = jwtTokenProvider.createToken(userInfoByToken.toEntity());

        } else {
            createdToken = jwtTokenProvider.createToken(userInfoByToken.toEntity());
        }
        return SocialLoginResponseDto.builder().token(createdToken).build();
    }


    // TODO : 커스텀 Exception 만들기
    @Transactional(readOnly = true)
    public void validateDuplicateEmail(String userEmail) {
        Optional<User> findUser = userRepository.findByEmail(userEmail);
        if (findUser.isPresent()) {
            throw new DuplicateException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    @Transactional(readOnly = true)
    public void validateDuplicateNickname(String nickName) {

        Optional<User> findUser = userRepository.findByNickName(nickName);
        if (findUser.isPresent()) {
            throw new DuplicateException(ErrorCode.DUPLICATE_NICK_NAME);
        }
    }

    @Transactional
    public void changePassword(User user, ChangePasswordDto changePasswordDto) {

        user.changeUserPassword(changePasswordDto.getPassword());
    }

    @Transactional
    public void deleteUser(User user) {

        User findUser = findUserById(user.getId());
        articleRepository.deleteByUser(findUser);
        userRepository.delete(findUser);
    }


    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
    }


    @Transactional
    public TokenResponseDto login(String email, String password) {
        PrincipalDetails principalDetails = loadUserByEmail(email);

        if (!passwordEncoder.matches(password, principalDetails.getPassword())) {
            throw new UserNotFoundException();
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails.getUsername(),
                principalDetails.getPassword(), principalDetails.getAuthorities());

        // refresh Token 생성
        String refreshToken = jwtTokenProvider.createRefreshToken(principalDetails.getUser());
        refreshTokenService.createOrChangeRefreshToken(refreshToken, principalDetails.getUser().getId());

        String accessToken = jwtTokenProvider.createToken(principalDetails.getUser());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new TokenResponseDto(refreshToken, accessToken);
    }

    @Transactional
    public String generateConfirmationToken(String email, LocalDateTime requestedAt) {
        findUserByEmail(email);
        return confirmationTokenService.createOrChangeConfirmationToken(email, requestedAt);
    }

    @Transactional
    public void permissionCheckByConfirmationToken(String requestEmail, String key, LocalDateTime requestedAt) {
        confirmationTokenService.isRightKey(requestEmail, key, requestedAt);
    }

    private PrincipalDetails loadUserByEmail(String email) {
        User user = findUserByEmail(email);
        return new PrincipalDetails(user);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
