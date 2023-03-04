package WebProject.withpet.comments.service;

import WebProject.withpet.articles.domain.Article;
import WebProject.withpet.comments.domain.Comment;
import WebProject.withpet.articles.domain.SpecArticle;
import WebProject.withpet.comments.dto.CreateCommentRequestDto;
import WebProject.withpet.articles.repository.ArticleRepository;
import WebProject.withpet.comments.dto.ViewCommentListDto;
import WebProject.withpet.comments.dto.ViewCommentListResponseDto;
import WebProject.withpet.comments.repository.CommentRepository;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.exception.UnauthorizedException;
import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.repository.UserRepository;
import WebProject.withpet.users.service.UserService;
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

    private final UserService userService;

    @Transactional
    public void createComment(Long userId, CreateCommentRequestDto dto) {

        User userInConnection = userService.findUserById(userId);

        //테이블 상속 전략을 조인이 아니라 single-table으로 설계해야 하는지 논의

        Article article = articleRepository.findById(dto.getArticleId())
            .orElseThrow(() -> new DataNotFoundException());

        if (article.isSpecArticle()) {
            article = specArticleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new DataNotFoundException());
        }

        Comment saveComment = dto.toEntity(userInConnection, article);

        if (dto.getCommentId() != null) {

            findCommentById(dto.getCommentId()).setParentAndChildren(saveComment);
            commentRepository.save(saveComment);
        } else {
            commentRepository.save(saveComment);
        }

    }

    @Transactional(readOnly = true)
    public ViewCommentListResponseDto scrollDownComments(Long lastCommentId, Long articleId,
        Integer size) {

        Slice<ViewCommentListDto> commentsList = commentRepository.getCommentsList(lastCommentId,
            articleId, PageRequest.ofSize(size));

        return ViewCommentListResponseDto.builder()
            .hasNext(commentsList.hasNext())
            .commentsList(commentsList.getContent())
            .build();

    }

    @Transactional(readOnly = true)
    public List<ViewCommentListDto> viewChildrenComments(Long commentId) {

        List<ViewCommentListDto> response = new ArrayList<>();

        commentRepository.getChildrenListById(commentId).forEach(child -> {
            response.add(
                ViewCommentListDto.builder()
                    .commentId(child.getId())
                    .profileImg(child.getUser().getProfileImg())
                    .nickName(child.getUser().getNickName())
                    .createdTime(child.getCreatedTime())
                    .modifiedTime(child.getModifiedTime())
                    .content(child.getContent())
                    .build());
        });

        return response;
    }

    @Transactional
    public void updateComment(User user, Long commentId, String content) {

        checkUserAuthorization(user, commentId);

        findCommentById(commentId).update(content);
    }

    @Transactional
    public void deleteComment(User user, Long commentId) {

        checkUserAuthorization(user, commentId);

        Comment findComment = findCommentById(commentId);
        findComment.deleteConnectionWithUser(userService.findUserById(user.getId()));

        commentRepository.delete(findComment);


    }

    public void checkUserAuthorization(User user, Long commentId) {

        Comment findComment = commentRepository.findById(commentId)
            .orElseThrow(() -> new DataNotFoundException());

        if (user.getId() != findComment.getUser().getId()) {
            throw new UnauthorizedException();
        }

    }

    public Comment findCommentById(Long commentId) {

        return commentRepository.findById(commentId)
            .orElseThrow(() -> new DataNotFoundException());
    }

}
