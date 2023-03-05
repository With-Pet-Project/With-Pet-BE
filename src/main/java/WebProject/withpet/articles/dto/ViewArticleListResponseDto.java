package WebProject.withpet.articles.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewArticleListResponseDto {

    private Boolean hasNext;

    @Builder.Default
    private List<ViewArticleListDto> viewArticleListDtoList = new ArrayList<>();
}
