package WebProject.withpet.articles.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleUpdateRequestDto {

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "장소1는 필수 입력 값입니다.")
    private String place1;

    @NotBlank(message = "장소2는 필수 입력 값입니다.")
    private String placce2;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String detailText;

    @Builder.Default
    @Nullable
    private List<ImageDto> images = new ArrayList<>();
}
