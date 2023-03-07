package WebProject.withpet.users.domain;

import WebProject.withpet.comments.domain.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profileImg;
    @NotNull
    private String nickName;
    @Email
    private String email;
    @NotNull
    private String password;
    private String provider;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public User(
        String profileImg,
        String nickName,
        String email,
        String password,
        String provider
    ) {
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.provider = provider;
    }

    public void changeUserProfileImg(String img) {
        this.profileImg = img;
    }

    public void updateUserNickName(String nickName) {
        this.nickName = nickName;
    }

    public void changeUserPassword(String password) {
        this.password = password;
    }

    public void updateUserprofileImg(String image){
        this.profileImg=image;
    }

}
