package WebProject.withpet.users.service;

import WebProject.withpet.articles.dto.MypageArticleDto;
import WebProject.withpet.articles.repository.ArticleRepository;
import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.dto.MypageChangeRequestDto;
import WebProject.withpet.users.dto.ViewMypageResponseDto;
import WebProject.withpet.users.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MypageService {

    private final UserRepository userRepository;

    private final ArticleRepository articleRepository;
    @Transactional
    public void changeUserInfo(User user, MypageChangeRequestDto mypageChangeRequestDto){

        if(mypageChangeRequestDto.getNickName()!=null)
        user.changeUserNickName(mypageChangeRequestDto.getNickName());

        if(mypageChangeRequestDto.getProfileImg()!=null)
        user.changeUserProfileImg(mypageChangeRequestDto.getProfileImg());

        userRepository.save(user);

    }

    @Transactional
    public ViewMypageResponseDto viewMypage(User user){

        List<MypageArticleDto> articlesList = articleRepository.findMypageArticelsByUserId(
            user.getId());

        return ViewMypageResponseDto.builder()
            .profileImg(user.getProfileImg())
            .nickName(user.getNickName())
            .articleList(articlesList)
            .build();
    }
}
