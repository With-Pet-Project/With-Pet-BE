package WebProject.withpet.articles.dto;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.Tag;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ViewUserAndArticleResponseDto {

    private String profileImg;

    private String nickName;

    private String titile;

    private Tag tag;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    private String detailText;

    private Integer likeCnt;

    private Long articleLikeId;


}
