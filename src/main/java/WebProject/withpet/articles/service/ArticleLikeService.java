package WebProject.withpet.articles.service;


import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.ArticleLike;
import WebProject.withpet.articles.dto.ArticleLikeRequestDto;
import WebProject.withpet.articles.repository.ArticleLikeRepository;
import WebProject.withpet.common.constants.ErrorCode;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.DuplicateException;
import WebProject.withpet.common.exception.UnauthorizedException;
import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;

    private final UserService userService;

    private final ArticleService articleService;

    @Transactional
    public void addArticleLike(User user, ArticleLikeRequestDto dto) {

        User findUser = userService.findUserById(user.getId());
        Article findArticle = articleService.findArticleById(dto.getArticleId());

        if (!articleLikeRepository.findByUserAndArticle(findUser, findArticle).isEmpty()) {
            throw new DuplicateException(ErrorCode.DUPLICATE_ARTICLE_LIKE);
        }

        ArticleLike createArticleLike = dto.toEntity(findUser, findArticle);
        findArticle.getArticleLikes().add(createArticleLike);
        articleLikeRepository.save(createArticleLike);

    }

    @Transactional
    public void cancelArticleLike(User user, ArticleLikeRequestDto dto) {

        User findUser = userService.findUserById(user.getId());
        Article findArticle = articleService.findArticleById(dto.getArticleId());

        ArticleLike findArticleLike = articleLikeRepository.findByUserAndArticle(findUser,
                findArticle)
            .orElseThrow(() -> new UnauthorizedException(ErrorCode.ARTICLE_LIKE_UNAUTHORIZED));

        findArticle.getArticleLikes().remove(findArticleLike);

    }

}
