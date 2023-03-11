package WebProject.withpet.articles.repository;

import WebProject.withpet.articles.domain.ArticleLike;
import WebProject.withpet.users.domain.User;

public interface ArticleLikeRepositoryCustom {

    ArticleLike findByUserAndArticleId(User user,Long articleId);

}
