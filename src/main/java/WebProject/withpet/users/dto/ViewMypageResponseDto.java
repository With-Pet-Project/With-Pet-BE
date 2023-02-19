package WebProject.withpet.users.dto;


import WebProject.withpet.articles.dto.MypageArticleDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewMypageResponseDto {

    private String profileImg;

    private String nickName;

    private List<MypageArticleDto> articleList;
}
