package WebProject.withpet.articles.repository;

import WebProject.withpet.articles.domain.SpecArticle;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecArticleRepository extends JpaRepository<SpecArticle,Long> {

    Optional<SpecArticle> findByTitle(String title);
}
