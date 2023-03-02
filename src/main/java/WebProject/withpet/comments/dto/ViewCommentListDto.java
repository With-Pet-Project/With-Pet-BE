package WebProject.withpet.comments.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewCommentListDto {

    private Long commentId;

    private String profileImg;

    private String nickName;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    private String content;
}
