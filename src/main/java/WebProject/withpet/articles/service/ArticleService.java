package WebProject.withpet.articles.service;


import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.Image;
import WebProject.withpet.articles.domain.SpecArticle;
import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.articles.dto.ArticleCreateRequestDto;
import WebProject.withpet.articles.dto.FileDto;
import WebProject.withpet.articles.dto.ImageDto;
import WebProject.withpet.articles.dto.ViewSpecificArticleResponseDto;
import WebProject.withpet.articles.dto.ViewUserAndArticleResponseDto;
import WebProject.withpet.articles.repository.ArticleRepository;
import WebProject.withpet.articles.repository.ImageRepository;
import WebProject.withpet.comments.domain.Comment;
import WebProject.withpet.comments.dto.ViewCommentList;
import WebProject.withpet.comments.repository.CommentRepository;
import WebProject.withpet.common.file.AwsS3Service;
import WebProject.withpet.users.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository<Article> articleRepository;

    private final ArticleRepository<SpecArticle> specArticleRepository;

    private final ImageRepository imageRepository;

    private final AwsS3Service awsS3Service;

    private final CommentRepository commentRepository;

    @Transactional
    public void createArticle(User user, ArticleCreateRequestDto articleCreateRequestDto,
        List<MultipartFile> files) {

        if (articleCreateRequestDto.getTag().equals(Tag.LOST) || articleCreateRequestDto.getTag()
            .equals(Tag.HOSPITAL) || articleCreateRequestDto.getTag().equals(Tag.WALK)) {

            SpecArticle specArticle = articleCreateRequestDto.toSpecArticleEntity(user);
            specArticleRepository.save(specArticle);

            if (files != null) {
                createImgAndInjectAwsImgUrl(specArticle, files);
            }


        } else {

            Article article = articleCreateRequestDto.toArticleEntity(user);
            articleRepository.save(article);

            if (files != null) {
                createImgAndInjectAwsImgUrl(article, files);
            }
        }
    }


    @Transactional
    public ViewSpecificArticleResponseDto viewSpecificArticle(Long articleId) {

        ViewSpecificArticleResponseDto response = articleRepository.findSpecificArticle(
            articleId);

        imageRepository.findAllByArticleId(articleId).forEach(image ->{
            response.getImages().add(ImageDto.builder().content(image.getContent()).build());
        });

        List<ViewCommentList> content = commentRepository.getCommentsList(0L, articleId,
            Pageable.ofSize(10)).getContent();

        response.setCommentListAndCommentCnt(content,response.getCommentCnt());

        return response;
    }


    @Transactional
    public void createImgAndInjectAwsImgUrl(Article article, List<MultipartFile> multipartFiles) {
        List<String> AwsImgUrl = awsS3Service.uploadImage(multipartFiles);

        AwsImgUrl.forEach(url -> {

            imageRepository.save(Image.builder()
                .article(article)
                .content(url)
                .build());
        });
    }
}

