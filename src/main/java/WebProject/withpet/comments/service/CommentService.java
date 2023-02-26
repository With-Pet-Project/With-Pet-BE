package WebProject.withpet.comments.service;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.comments.domain.Comment;
import WebProject.withpet.articles.domain.SpecArticle;
import WebProject.withpet.articles.dto.CreateCommentRequestDto;
import WebProject.withpet.articles.repository.ArticleRepository;
import WebProject.withpet.comments.dto.ViewCommentList;
import WebProject.withpet.comments.dto.ViewCommentListResponseDto;
import WebProject.withpet.comments.repository.CommentRepository;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.UnauthorizedException;
import WebProject.withpet.common.exception.UserNotFoundException;
import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final ArticleRepository<Article> articleRepository;

    private final ArticleRepository<SpecArticle> specArticleRepository;

    private final UserRepository userRepository;

    @Transactional
    public void createComment(Long userId, CreateCommentRequestDto dto) {

        User userInConnection = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException());

        //테이블 상속 전략을 조인이 아니라 single-table으로 설계해야 하는지 논의

        Article article = articleRepository.findById(dto.getArticleId())
            .orElseThrow(() -> new DataNotFoundException());

        if (article.isSpecArticle()) {
            article = specArticleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new DataNotFoundException());
        }

        Comment saveComment = dto.toEntity(userInConnection, article);

        if (dto.getCommentId() != null) {
            commentRepository.findById(dto.getCommentId())
                .orElseThrow(() -> new DataNotFoundException())
                .setParentAndChildren(saveComment);
            commentRepository.save(saveComment);
        } else {
            commentRepository.save(saveComment);
        }
    }

    @Transactional(readOnly = true)
    public ViewCommentListResponseDto scrollDownComments(Long lastCommentId, Long articleId,
        Integer size) {

        Slice<ViewCommentList> commentsList = commentRepository.getCommentsList(lastCommentId,
            articleId, PageRequest.ofSize(size));

        return ViewCommentListResponseDto.builder()
            .hasNext(commentsList.hasNext())
            .commentsList(commentsList.getContent())
            .build();

    }

    @Transactional(readOnly = true)
    public List<ViewCommentList> viewChildrenComments(Long commentId) {

        List<ViewCommentList> response = new ArrayList<>();

        commentRepository.getChildrenListById(commentId).forEach(child -> {
            response.add(
                ViewCommentList.builder()
                    .commentId(child.getId())
                    .profileImg(child.getUser().getProfileImg())
                    .nickName(child.getUser().getNickName())
                    .createdTime(child.getCreatedTime())
                    .content(child.getContent())
                    .build());
        });

        return response;
    }

    @Transactional
    public void updateComment(User user, Long commentId, String content) {

        if (checkUserAuthorization(user, commentId)) {
            throw new UnauthorizedException();
        }

        commentRepository.findById(commentId)
            .orElseThrow(() -> new DataNotFoundException()).update(content);
    }

    @Transactional
    public void deleteComment(User user, Long commentId) {

        if (checkUserAuthorization(user, commentId)) {
            throw new UnauthorizedException();
        }

        Comment findComment = commentRepository.findById(commentId)
            .orElseThrow(() -> new DataNotFoundException());

        findComment.deleteConnectionWithUser(
            userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException()));

        commentRepository.delete(findComment);

    }

    public Boolean checkUserAuthorization(User user, Long commentId) {

        Comment findComment = commentRepository.findById(commentId)
            .orElseThrow(() -> new DataNotFoundException());

        if (user.getId() != findComment.getUser().getId()) {
            return true;
        } else {
            return false;
        }
    }

}
