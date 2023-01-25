package WebProject.withpet.spec_articles;

import WebProject.withpet.articles.Article;
import WebProject.withpet.cities.City;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "spec_articles")
@Getter
@NoArgsConstructor
public class SpecArticle extends Article {


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;
}
