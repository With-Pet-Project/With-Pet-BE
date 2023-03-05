package WebProject.withpet.articles.service;


import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.Image;
import WebProject.withpet.articles.domain.SpecArticle;
import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.articles.dto.ArticleCreateRequestDto;
import WebProject.withpet.articles.dto.ArticleUpdateRequestDto;
import WebProject.withpet.articles.dto.ImageDto;
import WebProject.withpet.articles.dto.ViewArticleListDto;
import WebProject.withpet.articles.dto.ViewArticleListRequestDto;
import WebProject.withpet.articles.dto.ViewArticleListResponseDto;
import WebProject.withpet.articles.dto.ViewSpecificArticleResponseDto;
import WebProject.withpet.articles.repository.ArticleRepository;
import WebProject.withpet.articles.repository.ImageRepository;
import WebProject.withpet.comments.dto.ViewCommentListDto;
import WebProject.withpet.comments.repository.CommentRepository;
import WebProject.withpet.common.exception.DataNotFoundException;
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

    @Transactional
    public void createArticle(User user, ArticleCreateRequestDto articleCreateRequestDto) {

        if (articleCreateRequestDto.getTag().equals(Tag.LOST) || articleCreateRequestDto.getTag()
            .equals(Tag.HOSPITAL) || articleCreateRequestDto.getTag().equals(Tag.WALK)) {

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
    public ViewSpecificArticleResponseDto viewSpecificArticle(Long articleId) {

        ViewSpecificArticleResponseDto response = articleRepository.findSpecificArticle(
            articleId);

        imageRepository.findAllByArticleId(articleId).forEach(image -> {
            response.getImages().add(ImageDto.builder().content(image.getContent()).build());
        });

        List<ViewCommentListDto> content = commentRepository.getCommentsList(0L, articleId,
            Pageable.ofSize(10)).getContent();

        response.setCommentList(content);

        return response;
    }

    @Transactional
    public void updateArticlle(Long articleId, ArticleUpdateRequestDto dto) {

        Article findArticle = findArticleById(articleId);
        // if(findArticle.isSpecArticle())
    }

    @Transactional
    public ViewArticleListResponseDto scrollDownArticle(ViewArticleListRequestDto dto) {

        Slice<ViewArticleListDto> response = articleRepository.getArticleList(dto,
            Pageable.ofSize(dto.getSize()));

        return ViewArticleListResponseDto.builder()
            .hasNext(response.hasNext())
            .viewArticleListDtoList(response.getContent())
            .build();

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
}

