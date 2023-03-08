package WebProject.withpet.auth.service;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmail(String email, String confirmationKey) throws MessagingException, UnsupportedEncodingException;
}
