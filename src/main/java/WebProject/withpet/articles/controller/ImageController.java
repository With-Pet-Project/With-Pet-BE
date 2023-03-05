package WebProject.withpet.articles.controller;


import WebProject.withpet.articles.domain.Image;
import WebProject.withpet.articles.dto.ImageDto;
import WebProject.withpet.articles.service.ImageService;
import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.dto.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/image")
    public ResponseEntity<ApiResponse<List<ImageDto>>> registerImage(
        @RequestPart List<MultipartFile> images) {

        ApiResponse<List<ImageDto>> response = new ApiResponse<>(200,
            "이미지가 성공적으로 등록되었습니다", imageService.registerImages(images));

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
