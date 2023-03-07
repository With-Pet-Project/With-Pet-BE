package WebProject.withpet.articles.repository;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.SpecArticle;
import WebProject.withpet.articles.dto.MypageArticleDto;
import WebProject.withpet.users.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository<T extends Article> extends JpaRepository<T, Long>,
    ArticleRepositoryCustom, ArticleRepositoryPagingCustom {

    Optional<Article> findByUser(User user);

    void deleteByUser(User user);
}
