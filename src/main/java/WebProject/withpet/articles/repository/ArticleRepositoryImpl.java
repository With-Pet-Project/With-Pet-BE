package WebProject.withpet.articles.repository;

import WebProject.withpet.articles.dto.MypageArticleDto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.Query;

public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<MypageArticleDto> findMypageArticelsByUserId(Long userId) {

        List<MypageArticleDto> result = em.createQuery(
                "select new WebProject.withpet.articles.dto.MypageArticleDto(a.id, a.createdTime, a.detailText) "
                    + "from Article a where a.user.id = :userId", MypageArticleDto.class)
            .setParameter("userId", userId)
            .getResultList();

        return result;
    }

}
