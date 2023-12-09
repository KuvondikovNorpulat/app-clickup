package uz.kuvondikov.clickup.service;

import org.springframework.scheduling.annotation.Async;
import uz.kuvondikov.clickup.dto.MemberDTO;
import uz.kuvondikov.clickup.entity.EmailDetails;
import uz.kuvondikov.clickup.service.base.BaseService;

public interface EmailService extends BaseService {
    @Async
    void sendSimpleMail(EmailDetails details);

    @Async
    void sendActivationAccountMessage(String email, String verificationCode);

    @Async
    void sendForgetPasswordMessage(String email, String verificationCode);

    @Async
    void sendWorkspaceInvite(MemberDTO memberDTO);
}
