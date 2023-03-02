package WebProject.withpet.comment;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.comments.domain.Comment;
import WebProject.withpet.articles.dto.CreateCommentRequestDto;
import WebProject.withpet.articles.repository.ArticleRepository;
import WebProject.withpet.comments.repository.CommentRepository;
import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository<Article> articleRepository;

    @Test
    public void createComment() {

        CreateCommentRequestDto comment1 = new CreateCommentRequestDto(null, 1L, "부모");
        CreateCommentRequestDto comment2 = new CreateCommentRequestDto(1L, 2L, "자식1");
        CreateCommentRequestDto comment3 = new CreateCommentRequestDto(1L, 3L, "자식2");

        User findUser = userRepository.findById(1L).get();
        Article findArticle = articleRepository.findById(6L).get();

        Comment parent = comment1.toEntity(findUser, findArticle);
        Comment child1 = comment2.toEntity(findUser, findArticle);
        Comment child2 = comment3.toEntity(findUser, findArticle);

        parent.setParentAndChildren(child1);
        parent.setParentAndChildren(child2);

        commentRepository.save(parent);
        commentRepository.save(child1);
        commentRepository.save(child2);

        Assertions.assertThat(parent.getChildren().size()).isEqualTo(2);

    }
}