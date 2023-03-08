package WebProject.withpet.articles.repository;

import WebProject.withpet.articles.dto.MypageArticleDto;
import WebProject.withpet.articles.dto.ViewArticleDto;
import WebProject.withpet.articles.dto.ViewSpecificArticleResponseDto;
import java.util.List;

public interface ArticleRepositoryCustom {

    List<MypageArticleDto> findMypageArticelsByUserId(Long userId);

    ViewArticleDto findSpecificArticle(Long articleId);

}
