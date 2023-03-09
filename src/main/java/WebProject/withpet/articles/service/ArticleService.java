package WebProject.withpet.articles.service;


import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.Image;
import WebProject.withpet.articles.domain.SpecArticle;
import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.articles.dto.ArticleCreateRequestDto;
import WebProject.withpet.articles.dto.ArticleUpdateRequestDto;
import WebProject.withpet.articles.dto.ImageDto;
import WebProject.withpet.articles.dto.ViewArticleDto;
import WebProject.withpet.articles.dto.ViewArticleListRequestDto;
import WebProject.withpet.articles.dto.ViewArticleListResponseDto;
import WebProject.withpet.articles.dto.ViewSpecificArticleResponseDto;
import WebProject.withpet.articles.repository.ArticleRepository;
import WebProject.withpet.articles.repository.ImageRepository;
import WebProject.withpet.comments.dto.ViewCommentListDto;
import WebProject.withpet.comments.repository.CommentRepository;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.UnauthorizedException;
import WebProject.withpet.common.file.AwsS3Service;
import WebProject.withpet.users.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository<Article> articleRepository;

    private final ArticleRepository<SpecArticle> specArticleRepository;

    private final ImageRepository imageRepository;

    private final AwsS3Service awsS3Service;

    private final CommentRepository commentRepository;

    private final ImageService imageService;

    @Transactional
    public void createArticle(User user, ArticleCreateRequestDto articleCreateRequestDto) {

        if (Tag.isSpecTag(articleCreateRequestDto.getTag())) {

            SpecArticle specArticle = articleCreateRequestDto.toSpecArticleEntity(user);
            specArticleRepository.save(specArticle);

            createImgAndInjectAwsImgUrl(specArticle, articleCreateRequestDto.getImages());

        } else {

            Article article = articleCreateRequestDto.toArticleEntity(user);
            articleRepository.save(article);

            createImgAndInjectAwsImgUrl(article, articleCreateRequestDto.getImages());

        }
    }


    @Transactional
    public ViewSpecificArticleResponseDto viewSpecificArticle(User user, Long articleId) {

        ViewArticleDto dto = articleRepository.findSpecificArticle(
            articleId);

        whetherUserLikeArticle(user, dto);

        ViewSpecificArticleResponseDto responseDto = dto.toResponseDto();

        imageRepository.findAllByArticleId(articleId).forEach(image -> {
            responseDto.getImages().add(ImageDto.builder().content(image.getContent()).build());
        });

        List<ViewCommentListDto> content = commentRepository.getCommentsList(0L, articleId,
            Pageable.ofSize(10)).getContent();

        responseDto.setCommentListAndCommentCnt(content, content.size());

        return responseDto;
    }

    @Transactional
    public void updateArticle(User user, Long articleId, ArticleUpdateRequestDto dto) {

        Article findArticle = findArticleById(articleId);
        checkUserAuthorization(user, findArticle);

        if (findArticle.isSpecArticle()) {
            findSpecArticleById(articleId).update(dto.getTitle(), dto.getDetailText(),
                dto.getPlace1(), dto.getPlacce2());
        }

        if (!dto.getImages().isEmpty()) {
            dto.getImages().forEach(imageDto -> {
                if (imageDto.getExistence() == false) {
                    imageService.deleteImage(imageDto);
                } else {
                    Image image = imageRepository.findByContent(imageDto.getContent())
                        .orElseGet(() ->
                            Image.builder()
                                .article(findArticle)
                                .content(imageDto.getContent())
                                .build());

                    imageRepository.save(image);
                }
            });
        }
    }

    @Transactional
    public ViewArticleListResponseDto scrollDownArticle(User user, ViewArticleListRequestDto dto) {

        Article lastArticle = articleRepository.findById(dto.getLastArticleId()).orElse(null);

        Slice<ViewArticleDto> response = articleRepository.getArticleList(lastArticle, dto,
            Pageable.ofSize(dto.getSize()));

        if (response.isEmpty()) {
            return ViewArticleListResponseDto.returnEmpty();
        }

        List<ViewArticleDto> responseDto = response.getContent();

        responseDto.forEach(viewArticleListDto -> {
            whetherUserLikeArticle(user, viewArticleListDto);
        });

        return ViewArticleListResponseDto.builder()
            .lastArticleId(
                responseDto.get(responseDto.size() - 1).getArticleId())
            .hasNext(response.hasNext())
            .viewArticleListDto(responseDto)
            .build();

    }

    @Transactional
    public void deleteArticle(User user, Long articleId) {

        Article findArticle = findArticleById(articleId);
        checkUserAuthorization(user, findArticle);

        if (findArticle.isSpecArticle()) {
            SpecArticle findSpecArticle = findSpecArticleById(articleId);
            specArticleRepository.delete(findSpecArticle);
        } else {
            articleRepository.delete(findArticle);
        }
    }

    @Transactional
    public void createImgAndInjectAwsImgUrl(Article article, List<ImageDto> images) {

        images.forEach(dto -> {
            if (dto.getExistence() == true) {
                imageRepository.save(Image.builder()
                    .article(article)
                    .content(dto.getContent())
                    .build());
            } else {
                awsS3Service.deleteImage(dto.getContent());
            }
        });
    }

    @Transactional
    public Article findArticleById(Long articleId) {
        return articleRepository.findById(articleId)
            .orElseThrow(() -> new DataNotFoundException());
    }

    @Transactional
    public SpecArticle findSpecArticleById(Long articleId) {
        return specArticleRepository.findById(articleId)
            .orElseThrow(() -> new DataNotFoundException());
    }

    @Transactional
    public void checkUserAuthorization(User user, Article article) {

        if (user.getId() != article.getUser().getId()) {
            throw new UnauthorizedException();
        }
    }

    @Transactional
    public void whetherUserLikeArticle(User user, ViewArticleDto dto) {

        if (user != null) {
            if (user.getId() == dto.getArticleLikeUserId()) {
                dto.setWhetherLike(true);
            } else {
                dto.setWhetherLike(false);
            }
        } else {
            dto.setWhetherLike(false);
        }
    }
}

