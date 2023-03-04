package WebProject.withpet.articles.dto;

import WebProject.withpet.articles.domain.Tag;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewArticleListDto {

    private Long articleId;

    private String profileImg;

    private String nickName;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    private String detailText;

    private Long likeCnt;

    private Long commentCnt;

    private Tag tag;

    private Long articleLike;
}
