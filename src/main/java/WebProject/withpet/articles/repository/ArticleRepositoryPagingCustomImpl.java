package WebProject.withpet.articles.repository;

import static WebProject.withpet.articles.domain.QArticle.article;
import static WebProject.withpet.articles.domain.QArticleLike.articleLike;
import static WebProject.withpet.articles.domain.QSpecArticle.specArticle;
import static WebProject.withpet.articles.domain.Tag.isSpecTag;
import static WebProject.withpet.users.domain.QUser.user;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.Filter;
import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.articles.dto.ViewArticleDto;
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
    public Slice<ViewArticleDto> getArticlesList2(ViewArticleListRequestDto dto,
        Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<ViewArticleDto> response = queryFactory
            .select(Projections.constructor(ViewArticleDto.class,
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
    public Slice<ViewArticleDto> getArticleList(Article lastArticle,
        ViewArticleListRequestDto dto,
        Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<ViewArticleDto> response = queryFactory
            .select(Projections.constructor(ViewArticleDto.class,
                article.id, user.profileImg, user.nickName, article.title, article.createdTime,
                article.modifiedTime, article.detailText, article.likeCnt, article.comments.size(),
                article.tag))
            .from(article)
            .leftJoin(article.user, user)
            .leftJoin(specArticle).on(article.id.eq(specArticle.id))
            .where(tagEq(dto.getTag(), dto.getPlace1(), dto.getPlace2()),
                lastIdGtOrLt(lastArticle, dto), paramContain(dto.getParam()))
            .orderBy(filterEq(dto.getFilter()))
            .limit(pageable.getPageSize() + 1)
            .fetch();

        return checkLastPage(response, pageable);

    }


    private Slice<ViewArticleDto> checkLastPage(List<ViewArticleDto> articleList,
        Pageable pageable) {

        Boolean hasNext = false;

        if (articleList.size() > pageable.getPageSize()) {
            articleList.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(articleList, pageable, hasNext);
    }

    //?????? ????????? ?????? ?????????????????????.(tag,place1,place2 ?????? ????????? ?????? ?????????)
    private BooleanExpression tagEq(Tag tag, String place1, String place2) {
        //?????? ??????
        if (tag == null && place1 == null) {
            return null;

            //??????1??? ?????? ??????(ex.?????? ?????? ?????? ????????? ??????)
        } else if (tag == null && place1 != null) {
            return specArticle.place1.eq(place1);

            //?????? ??????(lost,walk,hospital)??? ?????? 1,2 ?????? ????????? ?????? ??????
        } else if (isSpecTag(tag) && (place1 != null && place2 != null)) {
            return article.tag.eq(tag)
                .and(specArticle.place1.eq(place1).and(specArticle.place2.eq(place2)));

            //?????? ??????(lost,walk,hospital)??? ?????? 1??? ?????? ????????? ?????? ??????(?????? 2??? ?????? ex.????????? ??????)
        } else if (isSpecTag(tag) && (place1 != null && place2 == null)) {
            return article.tag.eq(tag)
                .and(specArticle.place1.eq(place1));

            //?????? ???????????? ????????????(?????? ?????? x)
        } else {
            return article.tag.eq(tag);
        }
    }

    //?????? ????????? ?????? ?????????????????????.(filter??? ?????? ????????? ?????? ?????? ?????? ?????????)
    private BooleanExpression lastIdGtOrLt(Article lastArticle, ViewArticleListRequestDto dto) {
        //??? ????????? ????????? lastArticle ?????? null
        if (lastArticle == null) {
            return null;

            //filter??? ???????????????
        } else if (dto.getFilter().equals(Filter.POPULAR) && lastArticle != null) {
            return article.likeCnt.lt(lastArticle.getLikeCnt());

            //filter??? ???????????????
        } else {
            return article.modifiedTime.before(lastArticle.getModifiedTime());
        }

    }

    //????????? ???????????? ?????? ?????????????????????.
    private BooleanExpression paramContain(String param) {
        if (param == null) {
            return null;
        } else {
            return article.title.contains(param);
        }
    }

    //?????? ????????? ?????? ?????????????????????.(filter??? ?????? ????????? ?????? ??????.???????????? ?????????)
    private OrderSpecifier<?> filterEq(Filter filter) {
        return (filter.equals(Filter.RECENT)) ? article.modifiedTime.desc()
            : article.likeCnt.desc();

    }

}
