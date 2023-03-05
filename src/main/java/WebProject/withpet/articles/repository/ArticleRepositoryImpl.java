package WebProject.withpet.articles.repository;

import static WebProject.withpet.articles.domain.QArticle.*;
import static WebProject.withpet.articles.domain.QArticleLike.articleLike;
import static WebProject.withpet.articles.domain.QImage.image;
import static WebProject.withpet.articles.domain.QSpecArticle.specArticle;
import static WebProject.withpet.comments.domain.QComment.comment;
import static WebProject.withpet.users.domain.QUser.user;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.Filter;
import WebProject.withpet.articles.domain.Image;
import WebProject.withpet.articles.domain.QArticle;
import WebProject.withpet.articles.domain.QArticleLike;
import WebProject.withpet.articles.domain.QImage;
import WebProject.withpet.articles.domain.QSpecArticle;
import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.articles.dto.MypageArticleDto;
import WebProject.withpet.articles.dto.ViewArticleListDto;
import WebProject.withpet.articles.dto.ViewArticleListRequestDto;
import WebProject.withpet.articles.dto.ViewSpecificArticleResponseDto;
import WebProject.withpet.articles.dto.ViewUserAndArticleResponseDto;
import WebProject.withpet.comments.domain.QComment;
import WebProject.withpet.comments.dto.ViewCommentListDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

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
    public ViewSpecificArticleResponseDto findSpecificArticle(Long articleId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        ViewUserAndArticleResponseDto dto = queryFactory
            .select(Projections.constructor(ViewUserAndArticleResponseDto.class,
                user.profileImg, user.nickName, article))
            .from(article)
            .leftJoin(article.user, user)
            .where(article.id.eq(articleId))
            .fetchOne();

        return ViewSpecificArticleResponseDto.builder()
            .profileImg(dto.getProfileImg())
            .nickName(dto.getNickName())
            .createdTime(dto.getArticle().getCreatedTime())
            .modifiedTime(dto.getArticle().getModifiedTime())
            .detailText(dto.getArticle().getDetailText())
            .likeCnt(dto.getArticle().getLikeCnt())
            .commentCnt(dto.getArticle().getComments().size())
            .build();
    }


}
