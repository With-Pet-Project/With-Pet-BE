package WebProject.withpet.articles.repository;

import WebProject.withpet.articles.domain.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByArticleId(Long articleId);
}
