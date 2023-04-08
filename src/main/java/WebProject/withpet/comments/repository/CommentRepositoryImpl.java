package WebProject.withpet.comments.repository;


import static WebProject.withpet.comments.domain.QComment.*;
import static WebProject.withpet.users.domain.QUser.user;

import WebProject.withpet.comments.domain.Comment;
import WebProject.withpet.comments.domain.QComment;
import WebProject.withpet.comments.dto.ViewCommentListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final EntityManager em;

    @Override
    public Slice<ViewCommentListDto> getCommentsList(Long lastCommentId, Long articleId,
        Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<ViewCommentListDto> commentList = queryFactory
            .select(Projections.constructor(ViewCommentListDto.class,
                comment.id, user.profileImg, user.nickName, comment.createdTime,
                comment.modifiedTime, comment.content))
            .from(comment)
            .innerJoin(comment.user, user)
            .where(comment.article.id.eq(articleId), comment.id.gt(lastCommentId),
                comment.parent.isNull())
            .orderBy(comment.id.asc())
            .limit(pageable.getPageSize() + 1)
            .fetch();

        return checkLastPage(commentList, pageable);
    }

    @Override
    public List<Comment> getChildrenListById(Long parentId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QComment child = new QComment("child");
        QComment parent = new QComment("parent");

        return queryFactory
            .select(child)
            .from(child)
            .leftJoin(child.parent, parent)
            .fetchJoin()
            .where(child.parent.id.eq(parentId))
            .fetch();

    }

    private Slice<ViewCommentListDto> checkLastPage(List<ViewCommentListDto> commentList,
        Pageable pageable) {

        Boolean hasNext = false;

        if (commentList.size() > pageable.getPageSize()) {
            commentList.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(commentList, pageable, hasNext);
    }
}
