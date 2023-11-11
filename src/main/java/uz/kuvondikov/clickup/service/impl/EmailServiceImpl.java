package uz.kuvondikov.clickup.service.impl;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.kuvondikov.clickup.controller.base.AbstractController;
import uz.kuvondikov.clickup.entity.EmailDetails;
import uz.kuvondikov.clickup.exception.MailException;
import uz.kuvondikov.clickup.repository.AuthUserRepository;
import uz.kuvondikov.clickup.service.EmailService;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final String PATH = AbstractController.PATH;

    private final JavaMailSender javaMailSender;
    private final AuthUserRepository userRepository;

    @Value(value = "${spring.mail.username}")
    private String sender;

    @Override
    public void sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(mailMessage);
        } catch (Exception ignored) {
        }
    }

    @Async
    @Override
    public void sendActivationAccountMessage(String toEmail, String verificationCode) {
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            mailMessage.setRecipients(Message.RecipientType.TO, toEmail);
            mailMessage.setSubject("Please verify");
            String verificationUrl = "http://localhost:1313" + PATH + "/auth/active" + "?email=" + toEmail + "&verificationCode=" + verificationCode;
            mailMessage.setContent(String.format("""
                    <h1>If you registered out web-site, please verify your account via press link </h1>
                    <button style="background-color: #6666ee; color: white"><a href="%s">Verify account</a></button>""", verificationUrl), "text/html; charset=utf-8");
            javaMailSender.send(mailMessage);
        } catch (MessagingException e) {
            throw new MailException(e.getMessage());
        }
    }

    @Async
    @Override
    public void sendForgetPasswordMessage(String email, String verificationCode) {
        String verificationUrl = "http://localhost:1313" + PATH + "/auth/restart-password" + "?email=" + email + "&verificationCode=" + verificationCode;

        EmailDetails details = EmailDetails.builder().recipient(email).msgBody(verificationUrl).subject("APPLICATION CLICK UP").build();
        sendSimpleMail(details);
    }

}
