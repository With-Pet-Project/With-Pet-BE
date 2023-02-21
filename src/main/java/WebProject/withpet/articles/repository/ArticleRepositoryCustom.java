package WebProject.withpet.articles.repository;

import WebProject.withpet.articles.dto.MypageArticleDto;
import WebProject.withpet.articles.dto.ViewUserAndArticleResponseDto;
import java.util.List;

public interface ArticleRepositoryCustom {

    List<MypageArticleDto> findMypageArticelsByUserId(Long userId);

    ViewUserAndArticleResponseDto findSpecificArticle(Long articleId);
}
