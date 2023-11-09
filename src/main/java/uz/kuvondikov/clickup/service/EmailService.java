package uz.kuvondikov.clickup.service;

import uz.kuvondikov.clickup.entity.EmailDetails;
import uz.kuvondikov.clickup.service.base.BaseService;

import java.util.concurrent.CompletableFuture;

public interface EmailService extends BaseService {
    void sendSimpleMail(EmailDetails details);


    String sendMailWithAttachment(EmailDetails details);

    CompletableFuture<String> activeAccount(String email, String verificationCode);

    void sendEmailMessage(String email, String verificationCode);
}
