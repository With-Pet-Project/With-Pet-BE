package WebProject.withpet.articles.dto;


import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.SpecArticle;
import WebProject.withpet.articles.domain.SpecArticle.SpecArticleBuilder;
import WebProject.withpet.articles.domain.Tag;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleCreateRequestDto {


    private Tag tag;


    private String place1;


    private String place2;


    private String title;


    private String detailText;


    private List<ArticleCreateRequestImgDto> images = new ArrayList<>();


    @JsonCreator
    public ArticleCreateRequestDto(
        @JsonProperty("tag") Tag tag,
        @JsonProperty("place1") String place1,
        @JsonProperty("place2") String place2,
        @JsonProperty("title") String title,
        @JsonProperty("detailText") String detailText,
        @JsonProperty("images") List<ArticleCreateRequestImgDto> images) {

        this.tag = tag;
        this.place1 = place1;
        this.place2 = place2;
        this.title = title;
        this.detailText = detailText;
        this.images = images;
    }

    public Article toArticleEntity() {
        return Article.builder()
            .tag(tag)
            .detailText(detailText)
            .title(title)
            .build();
    }

    public SpecArticle toSpecArticleEntity() {
        return SpecArticle.SpecArticleBuilder()
            .tag(tag)
            .detailText(detailText)
            .title(title)
            .place1(place1)
            .place2(place2)
            .build();
    }

}
