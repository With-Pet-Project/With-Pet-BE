package WebProject.withpet.articles.repository;

import static WebProject.withpet.articles.domain.QArticle.article;
import static WebProject.withpet.articles.domain.QArticleLike.articleLike;
import static WebProject.withpet.articles.domain.QSpecArticle.specArticle;
import static WebProject.withpet.articles.domain.Tag.isSpecTag;
import static WebProject.withpet.users.domain.QUser.user;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.Filter;
import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.articles.dto.ViewArticleListDto;
import WebProject.withpet.articles.dto.ViewArticleListRequestDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

public class ArticleRepositoryPagingCustomImpl implements ArticleRepositoryPagingCustom {

    @PersistenceContext
    EntityManager em;

  /*  @Override
    public Slice<ViewArticleListDto> getArticlesList2(ViewArticleListRequestDto dto,
        Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<ViewArticleListDto> response = queryFactory
            .select(Projections.constructor(ViewArticleListDto.class,
                article.id, user.profileImg,
                user.nickName, article.createdTime, article.modifiedTime, article.detailText,
                article.likeCnt, article.comments.size(), article.tag, articleLike.id
            ))
            .from(article)
            .leftJoin(article.user, user)
            .leftJoin(article.articleLikes, articleLike)
            .where(tagEq(dto.getTag(), dto.getPlace1(), dto.getPlace2()),
                article.id.gt(dto.getLastArticleId()))
            .orderBy(filterEq(dto.getFilter()))
            .limit(pageable.getPageSize() + 1)
            .fetch();

        return checkLastPage(response, pageable);
    }
       */

    @Override
    public Slice<ViewArticleListDto> getArticleList(Article lastArticle,
        ViewArticleListRequestDto dto,
        Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<ViewArticleListDto> response = queryFactory
            .select(Projections.constructor(ViewArticleListDto.class,
                article.id, user.profileImg, user.nickName, article.title, article.createdTime,
                article.modifiedTime, article.detailText, article.likeCnt, article.comments.size(),
                article.tag, articleLike.user.id
            ))
            .from(article)
            .leftJoin(article.user, user)
            .leftJoin(article.articleLikes, articleLike)
            .leftJoin(specArticle).on(article.id.eq(specArticle.id))
            .where(tagEq(dto.getTag(), dto.getPlace1(), dto.getPlace2()),
                lastIdGtOrLt(lastArticle, dto), paramContain(dto.getParam()))
            .orderBy(filterEq(dto.getFilter()))
            .limit(pageable.getPageSize() + 1)
            .fetch();

        return checkLastPage(response, pageable);

    }


    private Slice<ViewArticleListDto> checkLastPage(List<ViewArticleListDto> articleList,
        Pageable pageable) {

        Boolean hasNext = false;

        if (articleList.size() > pageable.getPageSize()) {
            articleList.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(articleList, pageable, hasNext);
    }

    //동적 쿼리를 위해 사용되었습니다.(tag,place1,place2 동적 조건에 따른 필터링)
    private BooleanExpression tagEq(Tag tag, String place1, String place2) {
        //전체 조회
        if (tag == null && place1 == null) {
            return null;

            //지역1로 전체 조회(ex.태크 상관 없이 서울시 전체)
        } else if (tag == null && place1 != null) {
            return specArticle.place1.eq(place1);

            //특정 태그(lost,walk,hospital)와 장소 1,2 모두 조건에 있는 경우
        } else if (isSpecTag(tag) && (place1 != null && place2 != null)) {
            return article.tag.eq(tag)
                .and(specArticle.place1.eq(place1).and(specArticle.place2.eq(place2)));

            //특정 태그(lost,walk,hospital)와 장소 1가 모두 조건에 있는 경우(장소 2는 전체 ex.서울시 전체)
        } else if (isSpecTag(tag) && (place1 != null && place2 == null)) {
            return article.tag.eq(tag)
                .and(specArticle.place1.eq(place1));

            //특정 태그전체 조건일때(지역 상관 x)
        } else {
            return article.tag.eq(tag);
        }
    }

    //동적 쿼리를 위해 사용되었습니다.(filter의 동적 조건에 따른 커서 기준 필터링)
    private BooleanExpression lastIdGtOrLt(Article lastArticle, ViewArticleListRequestDto dto) {
        //첫 페이지 요청시 lastArticle 값은 null
        if (lastArticle == null) {
            return null;

            //filter가 인기순일때
        } else if (dto.getFilter().equals(Filter.POPULAR) && lastArticle != null) {
            return article.likeCnt.lt(lastArticle.getLikeCnt());

            //filter가 최신순일때
        } else {
            return article.modifiedTime.before(lastArticle.getModifiedTime());
        }

    }

    //검색어 필터링을 위해 사용되었습니다.
    private BooleanExpression paramContain(String param) {
        if (param == null) {
            return null;
        } else {
            return article.title.contains(param);
        }
    }

    //동적 쿼리를 위해 사용되었습니다.(filter의 동적 조건에 따른 오름.내림차순 필터링)
    private OrderSpecifier<?> filterEq(Filter filter) {
        return (filter.equals(Filter.RECENT)) ? article.modifiedTime.desc()
            : article.likeCnt.desc();

    }

}
