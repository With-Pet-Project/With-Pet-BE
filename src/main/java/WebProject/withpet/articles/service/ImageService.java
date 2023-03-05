package WebProject.withpet.articles.service;


import WebProject.withpet.articles.domain.Image;
import WebProject.withpet.articles.dto.ImageDto;
import WebProject.withpet.articles.repository.ImageRepository;
import WebProject.withpet.common.exception.DataNotFoundException;
import WebProject.withpet.common.file.AwsS3Service;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    private final AwsS3Service awsS3Service;
    @Transactional
    public List<ImageDto> registerImages(List<MultipartFile> files){

        List<String> images = awsS3Service.uploadImage(files);

        List<ImageDto> response = new ArrayList<>();

        images.forEach(s ->{
            response.add(ImageDto.builder().content(s).build());
        });

        return response;
    }



}
