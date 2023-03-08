package WebProject.withpet.articles.dto;

import WebProject.withpet.articles.domain.Filter;
import WebProject.withpet.articles.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewArticleListRequestDto {

    private Tag tag;

    private Filter filter;

    private String place1;

    private String place2;

    private Long lastArticleId;

    private Integer size;

    private String param;

}
