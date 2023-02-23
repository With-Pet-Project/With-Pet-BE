package WebProject.withpet.articles.dto;

import WebProject.withpet.articles.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ViewUserAndArticleResponseDto {

    private String profileImg;

    private String nickName;

    private Article article;


}
