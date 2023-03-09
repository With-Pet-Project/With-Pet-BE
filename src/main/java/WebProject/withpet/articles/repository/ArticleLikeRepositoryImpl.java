package WebProject.withpet.articles.repository;

import static WebProject.withpet.articles.domain.QArticleLike.articleLike;

import WebProject.withpet.articles.domain.ArticleLike;
import WebProject.withpet.users.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ArticleLikeRepositoryImpl implements ArticleLikeRepositoryCustom{

    @PersistenceContext
    EntityManager em;


    @Override
    public ArticleLike findByUserAndArticleId(User user, Long articleId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
            .selectFrom(articleLike)
            .where(articleLike.user.id.eq(user.getId()),articleLike.article.id.eq(articleId))
            .fetchOne();
    }
}
