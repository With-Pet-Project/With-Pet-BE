package WebProject.withpet.articles.service;


import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.articles.domain.Image;
import WebProject.withpet.articles.domain.SpecArticle;
import WebProject.withpet.articles.domain.Tag;
import WebProject.withpet.articles.dto.ArticleCreateRequestDto;
import WebProject.withpet.articles.dto.ArticleCreateRequestImgDto;
import WebProject.withpet.articles.repository.ArticleRepository;
import WebProject.withpet.articles.repository.ImageRepository;
import WebProject.withpet.users.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final ImageRepository imageRepository;

    @Transactional
    public void createArticle(User user, ArticleCreateRequestDto articleCreateRequestDto) {

        if (articleCreateRequestDto.getTag().equals(Tag.LOST) || articleCreateRequestDto.getTag()
            .equals(Tag.HOSPITAL) || articleCreateRequestDto.getTag().equals(Tag.WALK)) {
            SpecArticle specArticle = articleCreateRequestDto.toSpecArticleEntity();

            articleRepository.save(specArticle);
            articleRepository.flush();
            log.info("size:::::::{}",articleCreateRequestDto.getImages().size());
            if (articleCreateRequestDto.getImages().size() != 0) {
                createImageEntityAndCreateRelation(articleCreateRequestDto, specArticle);
            }

        } else {

            Article article = articleCreateRequestDto.toArticleEntity();
            articleRepository.save(article);
            articleRepository.flush();
            log.info("size:::::::{}",articleCreateRequestDto.getImages().size());
            if (articleCreateRequestDto.getImages().size() != 0) {
                createImageEntityAndCreateRelation(articleCreateRequestDto, article);
            }
        }
    }

    @Transactional
    public void createImageEntityAndCreateRelation(ArticleCreateRequestDto articleCreateRequestDto,
        Article article) {
        log.info("여기까지 진행1");
        log.info("{}",articleCreateRequestDto.getImages().get(0).getContent());
        log.info("{}",articleCreateRequestDto.getImages().get(1).getContent());
        for (ArticleCreateRequestImgDto dto : articleCreateRequestDto.getImages()) {

            log.info("여기까지 진행2");
            log.info("articl: {}", article.getCreatedTime());
            log.info("content: {}",dto.getContent());
            Image img = Image.builder()
                .article(article)
                .content(dto.getContent())
                .build();
            img.setArticle(article);
            imageRepository.save(img);

        }
    }


}

