package WebProject.withpet.articles.domain;


import WebProject.withpet.articles.dto.ArticleUpdateRequestDto;
import WebProject.withpet.comments.domain.Comment;
import WebProject.withpet.common.domain.BaseEntity;
import WebProject.withpet.users.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "articles")
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@NoArgsConstructor
@DiscriminatorValue("Article")
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private Tag tag;


    private Integer likeCnt;


    private String title;

    private String detailText;

    @OneToMany(mappedBy = "article")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleLike> articleLikes = new ArrayList<>();

    @Builder
    public Article(User user, Tag tag, Integer likeCnt, String title,
        String detailText) {
        this.user = user;
        this.tag = tag;
        this.likeCnt = likeCnt;
        this.title = title;
        this.detailText = detailText;
    }

    public Boolean isSpecArticle() {

        if (this.getTag().equals(Tag.LOST) || this.getTag().equals(Tag.HOSPITAL)
            || this.getTag().equals(Tag.WALK)) {
            return true;
        } else {
            return false;
        }
    }

    public void update(String title,String detailText) {

        if (title != null) {
            this.title = title;
        }

        if (title != null) {
            this.detailText = detailText;
        }

    }

}
