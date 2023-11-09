package uz.kuvondikov.clickup.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.kuvondikov.clickup.constant.ErrorMessages;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.entity.EmailDetails;
import uz.kuvondikov.clickup.exception.BadRequestException;
import uz.kuvondikov.clickup.exception.NotFoundException;
import uz.kuvondikov.clickup.repository.AuthUserRepository;
import uz.kuvondikov.clickup.service.EmailService;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

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

    @Override
    public String sendMailWithAttachment(EmailDetails details) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());

            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (MessagingException e) {

            return "Error while sending mail!!!";

        }
    }

    @Async
    @Override
    public CompletableFuture<String> activeAccount(String email, String verificationCode) {
        AuthUser user = userRepository.findAuthUsersByEmailAndDeletedFalse(email).orElseThrow(() -> new NotFoundException(ErrorMessages.USER_NOT_FOUND));
        if (!user.getVerificationCode().equals(verificationCode))
            throw new BadRequestException(ErrorMessages.USER_NOT_FOUND);/// TODO: 08/11/23 mos kelmagan verification code
        user.setEnabled(true);
        user.setVerificationCode(null);
        userRepository.save(user);
        return CompletableFuture.completedFuture("Your account active");
    }

    @Async
    public void sendEmailMessage(String email, String verificationCode) {
        String verificationUrl = "http://localhost:1313/api/auth/active" + "?email=" + email + "&verificationCode=" + verificationCode;
        EmailDetails details = EmailDetails.builder().recipient(email).msgBody(verificationUrl).subject("Spring Boot security").build();
        sendSimpleMail(details);
    }

}
