package WebProject.withpet.articles.repository;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.dto.MypageArticleDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(
        "select new WebProject.withpet.articles.dto.MypageArticleDto(a.id, a.createdTime, a.detailText) "
            + "from Article a where a.user.id = :userId")
    List<MypageArticleDto> findMypageArticelsByUserId(@Param("userId") Long userId);
}
