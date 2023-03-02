package WebProject.withpet.comments.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewCommentListResponseDto {

    private Boolean hasNext;

    @Builder.Default
    private List<ViewCommentListDto> commentsList = new ArrayList<>();
}
