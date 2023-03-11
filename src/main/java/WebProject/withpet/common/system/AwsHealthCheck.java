package WebProject.withpet.common.system;


import WebProject.withpet.common.constants.ResponseConstants;
import WebProject.withpet.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwsHealthCheck {

    @GetMapping("/health-check")
    public ResponseEntity<ApiResponse<Void>> targetGroupHealthCheck(){

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstants.RESPONSE_VIEW_OK);
    }
}
