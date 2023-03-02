package WebProject.withpet.articles.dto;


import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.Image;
import WebProject.withpet.articles.domain.SpecArticle;
import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.users.domain.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ArticleCreateRequestDto {


    private Tag tag;


    private String place1;


    private String place2;


    private String title;


    private String detailText;


    private List<ImageDto> images=new ArrayList<>();


    @JsonCreator
    public ArticleCreateRequestDto(
        @JsonProperty("tag") Tag tag,
        @JsonProperty("place1") String place1,
        @JsonProperty("place2") String place2,
        @JsonProperty("title") String title,
        @JsonProperty("detailText") String detailText,
        @JsonProperty("images") List<ImageDto> images) {

        this.tag = tag;
        this.place1 = place1;
        this.place2 = place2;
        this.title = title;
        this.detailText = detailText;
        this.images=images;
    }

    public Article toArticleEntity(User user) {
        return Article.builder()
            .user(user)
            .tag(tag)
            .detailText(detailText)
            .title(title)
            .likeCnt(0)
            .build();
    }

    public SpecArticle toSpecArticleEntity(User user) {
        return SpecArticle.SpecArticleBuilder()
            .user(user)
            .tag(tag)
            .detailText(detailText)
            .title(title)
            .place1(place1)
            .place2(place2)
            .likeCnt(0)
            .build();
    }

}
