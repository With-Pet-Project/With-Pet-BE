package WebProject.withpet.articles.repository;

import WebProject.withpet.articles.dto.MypageArticleDto;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface ArticleRepositoryCustom {

    List<MypageArticleDto> findMypageArticelsByUserId(Long userId);
}
