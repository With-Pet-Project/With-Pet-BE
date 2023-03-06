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

    private String title;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    private String detailText;

    private Integer likeCnt;

    private Integer commentCnt;

    private Tag tag;

    private Long articleLikeUserId;

    private Boolean whetherLike;

    public ViewArticleListDto(Long articleId, String profileImg, String nickName, String title,
        LocalDateTime createdTime, LocalDateTime modifiedTime, String detailText, Integer likeCnt,
        Integer commentCnt, Tag tag, Long articleLikeUserId) {
        this.articleId = articleId;
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.title = title;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.detailText = detailText;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.tag = tag;
        this.articleLikeUserId = articleLikeUserId;
    }

    public void setWhetherLike(Boolean b){
        whetherLike=b;
    }
}
