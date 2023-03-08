package WebProject.withpet.articles.repository;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.ArticleLike;
import WebProject.withpet.users.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike,Long> {

    Optional<ArticleLike> findByUser(User user);

    Optional<ArticleLike> findByUserAndArticle(User user, Article article);
}
