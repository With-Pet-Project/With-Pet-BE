package WebProject.withpet.articles.repository;

import static WebProject.withpet.articles.domain.QArticle.*;
import static WebProject.withpet.users.domain.QUser.user;

import WebProject.withpet.articles.dto.MypageArticleDto;
import WebProject.withpet.articles.dto.ViewUserAndArticleResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<MypageArticleDto> findMypageArticelsByUserId(Long userId) {

        List<MypageArticleDto> result = em.createQuery(
                "select new WebProject.withpet.articles.dto.MypageArticleDto(a.id, a.createdTime, a.detailText) "
                    + "from Article a where a.user.id = :userId", MypageArticleDto.class)
            .setParameter("userId", userId)
            .getResultList();

        return result;
    }

    @Override
    public ViewUserAndArticleResponseDto findSpecificArticle(Long articleId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
            .select(Projections.constructor(ViewUserAndArticleResponseDto.class,
                user.profileImg,
                user.nickName,
                article))
            .from(article)
            .leftJoin(article.user, user)
            .where(article.id.eq(articleId))
            .fetchOne();
    }

}
