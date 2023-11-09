package uz.kuvondikov.clickup.service;

import org.springframework.scheduling.annotation.Async;
import uz.kuvondikov.clickup.entity.EmailDetails;
import uz.kuvondikov.clickup.service.base.BaseService;

import java.util.concurrent.CompletableFuture;

public interface EmailService extends BaseService {
    void sendSimpleMail(EmailDetails details);


    String sendMailWithAttachment(EmailDetails details);

    CompletableFuture<String> activeAccount(String email, String verificationCode);

    void sendActivationAccountMessage(String email, String verificationCode);

    @Async
    void sendForgetPasswordMessage(String email, String verificationCode);
}
