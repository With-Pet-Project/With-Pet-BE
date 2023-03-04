package WebProject.withpet.articles.dto;


import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.ArticleLike;
import WebProject.withpet.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLikeRequestDto {


    private Long articleId;


    public ArticleLike toEntity(User user, Article article) {

        return ArticleLike.builder()
            .user(user)
            .article(article)
            .build();
    }
}
