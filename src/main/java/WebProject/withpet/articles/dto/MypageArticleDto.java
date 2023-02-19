package WebProject.withpet.articles.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MypageArticleDto {

    private Long articleId;
    private LocalDateTime createdTime;
    private String content;
}
