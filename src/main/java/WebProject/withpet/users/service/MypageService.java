package WebProject.withpet.users.service;

import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.dto.MypageChangeRequestDto;
import WebProject.withpet.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    @Transactional
    public void changeUserInfo(User user, MypageChangeRequestDto mypageChangeRequestDto){

        if(mypageChangeRequestDto.getNickName()!=null)
        user.changeUserNickName(mypageChangeRequestDto.getNickName());

        if(mypageChangeRequestDto.getProfileImg()!=null)
        user.changeUserProfileImg(mypageChangeRequestDto.getProfileImg());

        userRepository.save(user);

    }
}
