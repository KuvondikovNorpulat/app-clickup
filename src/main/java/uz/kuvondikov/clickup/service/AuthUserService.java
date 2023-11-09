package uz.kuvondikov.clickup.service;

import uz.kuvondikov.clickup.dto.PaginationDTO;
import uz.kuvondikov.clickup.dto.user.*;
import uz.kuvondikov.clickup.service.base.BaseService;

import java.util.List;

public interface AuthUserService extends BaseService {
    Long register(AuthUserRegisterDto userRegisterDto);

    SessionDTO login(AuthUserLoginDto loginDto);

    AuthUserDto getById(Long id);

    List<AuthUserDto> getAll();

    PaginationDTO<List<AuthUserDto>> getList(int page, int size);

    Long update(AuthUserUpdateDto updateDto);

    Long delete(Long id);

    Long accountActivation(String email, String verificationCode);

    Long forgetPassword(String email);

    Long restartPassword(String email, String verificationCode, String newPassword);
}
