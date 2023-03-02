package WebProject.withpet.comments.domain;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.common.domain.BaseEntity;
import WebProject.withpet.users.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private String content;

    @Builder
    public Comment(User user, Article article, String content) {
        setUser(user);
        setArticle(article);
        this.content = content;
    }


    public void setUser(User user) {

        if (this.user != null) {
            this.user.getComments().remove(this);
        }

        this.user = user;
        user.getComments().add(this);
    }

    public void setArticle(Article article) {

        if (this.article != null) {
            this.article.getComments().remove(this);
        }

        this.article = article;
        article.getComments().add(this);
    }

    public void setParentAndChildren(Comment child) {
        child.parent = this;
        this.getChildren().add(child);
    }

    public void update(String content){
        this.content = content;
    }

    public void deleteConnectionWithUser(User user){
        this.getUser().getComments().remove(this);
    }
}
