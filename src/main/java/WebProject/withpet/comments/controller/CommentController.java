package WebProject.withpet.comments.controller;

import WebProject.withpet.comments.dto.CreateCommentRequestDto;
import WebProject.withpet.comments.dto.ViewCommentListDto;
import WebProject.withpet.comments.dto.ViewCommentListResponseDto;
import WebProject.withpet.comments.service.CommentService;
import WebProject.withpet.common.auth.PrincipalDetails;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.dto.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<ApiResponse<Void>> createComment(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody @Valid CreateCommentRequestDto createCommentRequestDto) {

        commentService.createComment(principalDetails.getUser().getId(), createCommentRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseConstants.RESPONSE_SAVE_OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<ApiResponse<ViewCommentListResponseDto>> scrollDownComments(
        @RequestParam("lastCommentId") Long lastCommentId,
        @RequestParam("articleId") Long articleId,
        @RequestParam("size") Integer size) {

        ApiResponse<ViewCommentListResponseDto> response = new ApiResponse<>(200, "댓글 목록 조회 성공",
            commentService.scrollDownComments(lastCommentId, articleId, size));

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<List<ViewCommentListDto>>> viewChildrenComments(
        @PathVariable("commentId") @NotNull(message = "부모 댓글 id는 필수 값입니다.") Long commentId) {

        ApiResponse<List<ViewCommentListDto>> response = new ApiResponse<>(200, "대댓글 조회 성공",
            commentService.viewChildrenComments(commentId));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse<Void>> updateComment(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("commentId") @NotNull(message = "변경할 댓글 id는 필수 값입니다.") Long commentId,
        @RequestParam("content") String content) {

        commentService.updateComment(principalDetails.getUser(), commentId, content);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstants.RESPONSE_UPDATE_OK);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("commentId") Long commentId) {

        commentService.deleteComment(principalDetails.getUser(),commentId);

        return  ResponseEntity.status(HttpStatus.OK).body(ResponseConstants.RESPONSE_DEL_OK);
    }
}
