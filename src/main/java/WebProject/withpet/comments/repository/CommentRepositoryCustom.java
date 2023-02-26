package WebProject.withpet.comments.repository;

import WebProject.withpet.comments.domain.Comment;
import WebProject.withpet.comments.dto.ViewCommentList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepositoryCustom {

    Slice<ViewCommentList> getCommentsList(Long lastCommentId, Long articleId, Pageable pageable);

    List<Comment> getChildrenListById(Long parentId);
}
