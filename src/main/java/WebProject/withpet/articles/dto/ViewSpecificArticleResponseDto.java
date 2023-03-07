package WebProject.withpet.articles.dto;

import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.comments.dto.ViewCommentListDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ViewSpecificArticleResponseDto {


    private String profileImg;

    private String nickName;

    private String titile;

    private Tag tag;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    private String detailText;

    private Integer likeCnt;

    private Integer commentCnt;


    private List<ImageDto> images = new ArrayList<>();

    private List<ViewCommentListDto> commentList = new ArrayList<>();

    private Long articleLikeUserId;

    private Boolean whetherLike;

    @Builder
    public ViewSpecificArticleResponseDto(String profileImg, String nickName, String title, Tag tag,
        LocalDateTime createdTime, LocalDateTime modifiedTime, String detailText, Integer likeCnt,
        Integer commentCnt, Long articleLikeUserId) {
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.titile = title;
        this.tag = tag;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.detailText = detailText;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.articleLikeUserId = articleLikeUserId;
    }

    public void setCommentListAndCommentCnt(List<ViewCommentListDto> commentList,
        Integer commentCnt) {
        this.commentList = commentList;
        this.commentCnt = commentCnt;
    }

    public void setWhetherLike(Boolean b) {
        this.whetherLike = b;
    }

}
