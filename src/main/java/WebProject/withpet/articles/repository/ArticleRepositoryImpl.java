package WebProject.withpet.articles.repository;

import static WebProject.withpet.articles.domain.QArticle.*;
import static WebProject.withpet.articles.domain.QArticleLike.articleLike;
import static WebProject.withpet.comments.domain.QComment.comment;
import static WebProject.withpet.users.domain.QUser.user;

import WebProject.withpet.articles.dto.MypageArticleDto;
import WebProject.withpet.articles.dto.ViewArticleDto;
import WebProject.withpet.articles.dto.ViewSpecificArticleResponseDto;
import WebProject.withpet.comments.domain.Comment;
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
    public ViewArticleDto findSpecificArticle(Long articleId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

       return queryFactory
            .select(Projections.constructor(ViewArticleDto.class, article.id, user.profileImg,
                user.nickName, article.title, article.createdTime,
                article.modifiedTime, article.detailText, article.likeCnt, article.comments.size(),
                article.tag, articleLike.user.id
            ))
            .from(article)
            .leftJoin(article.user, user)
            .leftJoin(article.articleLikes, articleLike)
            .where(article.id.eq(articleId))
            .fetchOne();

    }


}
