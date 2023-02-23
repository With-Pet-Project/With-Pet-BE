package WebProject.withpet.articles.domain;

import WebProject.withpet.articles.domain.Article;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "images")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private String content;

    @Builder
    public Image(Article article, String content) {
        setArticle(article);
        this.content = content;
    }

    public void setArticle(Article article){

        if(this.article!=null)
            this.article.getImages().remove(this);

        this.article=article;
        this.getArticle().getImages().add(this);
    }


}
