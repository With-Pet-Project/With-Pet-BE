package WebProject.withpet.articles.repository;

import WebProject.withpet.articles.dto.ViewArticleListDto;
import WebProject.withpet.articles.dto.ViewArticleListRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleRepositoryPagingCustom {

    //주석 처리된 인터페이스는 추후 성능 최적화할때 테스트용 사용하고자 만들었습니다.
    //Slice<ViewArticleListDto> getArticlesList2(ViewArticleListRequestDto dto, Pageable pageable);

    Slice<ViewArticleListDto> getArticleList(ViewArticleListRequestDto dto, Pageable pageable);
}
