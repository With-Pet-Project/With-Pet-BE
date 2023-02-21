package WebProject.withpet.articles.service;


import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.Image;
import WebProject.withpet.articles.domain.SpecArticle;
import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.articles.dto.ArticleCreateRequestDto;
import WebProject.withpet.articles.dto.FileDto;
import WebProject.withpet.articles.dto.ViewSpecificArticleResponseDto;
import WebProject.withpet.articles.dto.ViewUserAndArticleResponseDto;
import WebProject.withpet.articles.repository.ArticleRepository;
import WebProject.withpet.articles.repository.ImageRepository;
import WebProject.withpet.common.file.AwsS3Service;
import WebProject.withpet.users.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final ImageRepository imageRepository;

    private final AwsS3Service awsS3Service;

    @Transactional
    public void createArticle(User user, ArticleCreateRequestDto articleCreateRequestDto,
        List<MultipartFile> files) {

        if (articleCreateRequestDto.getTag().equals(Tag.LOST) || articleCreateRequestDto.getTag()
            .equals(Tag.HOSPITAL) || articleCreateRequestDto.getTag().equals(Tag.WALK)) {

            SpecArticle specArticle = articleCreateRequestDto.toSpecArticleEntity(user);
            articleRepository.save(specArticle);

            createImgAndInjectAwsImgUrl(specArticle, files);


        } else {

            Article article = articleCreateRequestDto.toArticleEntity(user);
            articleRepository.save(article);

            createImgAndInjectAwsImgUrl(article, files);
        }
    }


    @Transactional
    public ViewSpecificArticleResponseDto viewSpecificArticle(Long articleId) {

        ViewUserAndArticleResponseDto result = articleRepository.findSpecificArticle(
            articleId);

        ViewSpecificArticleResponseDto response = ViewSpecificArticleResponseDto.builder()
            .profileImg(result.getProfileImg())
            .nickName(result.getNickName())
            .createdTime(result.getArticle().getCreatedTime())
            .modifiedTime(result.getArticle().getModifiedTime())
            .detailText(result.getArticle().getDetailText())
            .likeCnt(result.getArticle().getLikeCnt())
            .build();

        for (Image image : result.getArticle().getImages()) {
            response.getImages().add(image.getContent());
        }

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

