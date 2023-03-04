package WebProject.withpet.articles.repository;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.articles.dto.MypageArticleDto;
import WebProject.withpet.articles.dto.ViewArticleListDto;
import WebProject.withpet.articles.dto.ViewArticleListRequestDto;
import WebProject.withpet.articles.dto.ViewSpecificArticleResponseDto;
import WebProject.withpet.articles.dto.ViewUserAndArticleResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleRepositoryCustom {

    List<MypageArticleDto> findMypageArticelsByUserId(Long userId);

    ViewSpecificArticleResponseDto findSpecificArticle(Long articleId);

    Slice<ViewArticleListDto> getArticlesList(ViewArticleListRequestDto dto, Pageable pageable);

    Slice<ViewArticleListDto> getSpecArticleList(ViewArticleListRequestDto dto, Pageable pageable);

}
