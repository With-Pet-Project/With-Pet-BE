package WebProject.withpet.users.service;

import WebProject.withpet.articles.dto.MypageArticleDto;
import WebProject.withpet.articles.repository.ArticleRepository;
import WebProject.withpet.common.file.AwsS3Service;
import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.dto.MypageChangeRequestDto;
import WebProject.withpet.users.dto.ViewMypageResponseDto;
import WebProject.withpet.users.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;

    private final ArticleRepository articleRepository;

    private final UserService userService;

    private final AwsS3Service awsS3Service;

    @Transactional
    public void changeUserInfo(User user, String nickName, List<MultipartFile> images) {

        User findUser = userService.findUserById(user.getId());

        if (nickName != null) {
            findUser.updateUserNickName(nickName);
        }

        if (images != null) {
            List<String> imageUrl = awsS3Service.uploadImage(images);
           findUser.updateUserprofileImg(imageUrl.get(0));
        }

    }

    @Transactional
    public ViewMypageResponseDto viewMypage(User user) {

        List<MypageArticleDto> articlesList = articleRepository.findMypageArticelsByUserId(
            user.getId());

        return ViewMypageResponseDto.builder()
            .email(user.getEmail())
            .profileImg(user.getProfileImg())
            .nickName(user.getNickName())
            .articleList(articlesList)
            .build();
    }
}
