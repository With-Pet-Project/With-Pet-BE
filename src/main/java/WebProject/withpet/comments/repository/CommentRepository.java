package WebProject.withpet.comments.repository;

import WebProject.withpet.comments.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>,CommentRepositoryCustom {

    List<Comment> findAllByParent(Comment parent);
}
