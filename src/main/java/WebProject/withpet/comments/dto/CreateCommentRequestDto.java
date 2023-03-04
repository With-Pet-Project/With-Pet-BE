package WebProject.withpet.comments.dto;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.comments.domain.Comment;
import WebProject.withpet.users.domain.User;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentRequestDto {

    @Nullable
    private Long commentId;

    @NotNull(message = "댓글을 단 게시글의 아이디 값은 필수 값입니다.")
    private Long articleId;

    @NotBlank(message = "댓글 내용은 필수 값입니다.")
    private String content;

    public Comment toEntity(User user, Article article) {
        return Comment.builder()
            .user(user)
            .article(article)
            .content(content)
            .build();
    }
}
