package WebProject.withpet.articles.dto;

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

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    private String detailText;

    private Integer likeCnt;

    private Integer commentCnt;


    private List<ImageDto> images = new ArrayList<>();

    private List<ViewCommentListDto> commentList = new ArrayList<>();

    @Builder
    public ViewSpecificArticleResponseDto(String profileImg, String nickName,
        LocalDateTime createdTime,
        LocalDateTime modifiedTime, String detailText, Integer likeCnt, Integer commentCnt) {
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.detailText = detailText;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
    }

    public void setCommentList(List<ViewCommentListDto> commentList) {
        this.commentList = commentList;
    }

}
