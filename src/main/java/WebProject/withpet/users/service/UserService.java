package WebProject.withpet.users.service;

import WebProject.withpet.common.auth.application.JwtTokenProvider;
import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.dto.SocialLoginResponseDto;
import WebProject.withpet.users.dto.SocialUserInfoDto;
import WebProject.withpet.users.dto.UserRequestDto;
import WebProject.withpet.users.repository.UserRepository;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
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

    public void register(@Valid UserRequestDto userRequestDto) {
        validateDuplicateEmail(userRequestDto.getEmail());
        validateDuplicateNickname(userRequestDto.getNickname());

        User user = User.builder()
            .nickName(userRequestDto.getNickname())
            .email(userRequestDto.getEmail())
            .password(passwordEncoder.encode(userRequestDto.getPassword()))
            .build();

        userRepository.save(user);
    }
    @Transactional
    public SocialLoginResponseDto socialLogin(String code) throws JSONException {

        String accessToken = userSocialService.getAccessToken(code);
        SocialUserInfoDto userInfoByToken = userSocialService.getUserInfoByToken(accessToken);

        String createdToken;

        if (userRepository.findByEmail(userInfoByToken.getEmail()).isEmpty()) {

            //회원가입 진행 후 토큰 생성
            userRepository.save(
                User.builder()
                    .email(userInfoByToken.getEmail())
                    .password("")
                    .nickName(userInfoByToken.getNickname())
                    .build()
            );

            createdToken = jwtTokenProvider.createToken(userInfoByToken.toEntity());

        } else {
            createdToken = jwtTokenProvider.createToken(userInfoByToken.toEntity());
        }
        return SocialLoginResponseDto
            .builder()
            .token(createdToken)
            .build();
    }


    // TODO : 커스텀 Exception 만들기
    private void validateDuplicateEmail(String userEmail) {
        Optional<User> findUser = userRepository.findByEmail(userEmail);
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("동일한 이메일을 사용하는 사용자가 이미 존재합니다.");
        }
    }

    private void validateDuplicateNickname(String nickName) {
        Optional<User> findUser = userRepository.findByNickName(nickName);
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("동일한 닉네임을 사용하는 사용자가 이미 존재합니다.");
        }
    }
}
