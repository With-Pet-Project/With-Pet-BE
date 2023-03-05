package WebProject.withpet.articles.dto;

import WebProject.withpet.articles.domain.Tag;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ViewArticleListDto {

    private Long articleId;

    private String profileImg;

    private String nickName;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    private String detailText;

    private Integer likeCnt;

    private Integer commentCnt;

    private Tag tag;

    private Long articleLikeId;

    public ViewArticleListDto(Long articleId, String profileImg, String nickName,
        LocalDateTime createdTime, LocalDateTime modifiedTime, String detailText, Integer likeCnt,
        Integer commentCnt, Tag tag, Long articleLikeId) {
        this.articleId = articleId;
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.detailText = detailText;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.tag = tag;
        this.articleLikeId = articleLikeId;
    }
}
