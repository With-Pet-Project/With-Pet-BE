package WebProject.withpet.articles.domain;

import WebProject.withpet.users.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "spec_articles")
@Getter
@NoArgsConstructor
@DiscriminatorValue("SpecArticle")
public class SpecArticle extends Article {

    String place1;

    String place2;

    //부모 클래스 builder와 다른 이름으로 생성
    @Builder(builderMethodName = "SpecArticleBuilder")
    public SpecArticle(User user, Tag tag, Integer likeCnt, Integer commentCnt, String title,
        String detailText, String place1, String place2) {
        super(user, tag, likeCnt, commentCnt, title, detailText);
        this.place1 = place1;
        this.place2 = place2;
    }
}
