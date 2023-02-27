package WebProject.withpet.articles.repository;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.dto.MypageArticleDto;
import WebProject.withpet.articles.dto.ViewSpecificArticleResponseDto;
import WebProject.withpet.articles.dto.ViewUserAndArticleResponseDto;
import java.util.List;

public interface ArticleRepositoryCustom {

    List<MypageArticleDto> findMypageArticelsByUserId(Long userId);

    ViewSpecificArticleResponseDto findSpecificArticle(Long articleId);

}
