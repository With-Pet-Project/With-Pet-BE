package WebProject.withpet.auth.service;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    @Value("${AdminMail.id}")
    private String adminEmailId;

    @Override
    public void sendEmail(String email, String confirmationKey)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = generateMessage(email, confirmationKey);
        mailSender.send(message);
    }

    private MimeMessage generateMessage(String email, String confirmationKey)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setSubject("[with pet] 이메일 인증");
        message.addRecipients(RecipientType.TO, email);

        String content = "";
        content += "<div style='margin:20px;'>";
        content += "<br>";
        content += "<p>아래 코드를 복사해 입력해주세요<p>";
        content += "<br>";
        content += "<br>";
        content += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        content += "<h3 style='color:blue;'> 귀하의 인증 코드입니다.</h3>";
        content += "<div style='font-size:130%'>";
        content += "<strong>";
        content += confirmationKey + "</strong><div><br/> ";
        content += "</div>";

        message.setText(content, "utf-8", "html");
        message.setFrom(new InternetAddress(adminEmailId, "with-pet"));
        return message;
    }


}
