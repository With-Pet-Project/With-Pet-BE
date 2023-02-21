package WebProject.withpet.articles.dto;

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

    private List<String> images= new ArrayList<>();

    @Builder
    public ViewSpecificArticleResponseDto(String profileImg, String nickName,
        LocalDateTime createdTime,
        LocalDateTime modifiedTime, String detailText, Integer likeCnt) {
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.detailText = detailText;
        this.likeCnt = likeCnt;

    }
}
