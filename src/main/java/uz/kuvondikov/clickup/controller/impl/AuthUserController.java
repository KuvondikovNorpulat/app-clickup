package uz.kuvondikov.clickup.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.kuvondikov.clickup.controller.AuthController;
import uz.kuvondikov.clickup.controller.base.AbstractController;
import uz.kuvondikov.clickup.dto.PaginationDTO;
import uz.kuvondikov.clickup.dto.response.DataDTO;
import uz.kuvondikov.clickup.dto.user.*;
import uz.kuvondikov.clickup.service.AuthUserService;

import java.util.List;

@RestController
public class AuthUserController extends AbstractController<AuthUserService> implements AuthController {
    public AuthUserController(AuthUserService service) {
        super(service);
    }

    @Override
    public ResponseEntity<DataDTO<Long>> register(AuthUserRegisterDto userRegisterDto) {
        Long registerUserId = service.register(userRegisterDto);
        return ResponseEntity.ok(new DataDTO<>(registerUserId));
    }

    @Override
    public ResponseEntity<DataDTO<SessionDTO>> login(AuthUserLoginDto authUserLoginDto) {
        SessionDTO sessionDTO = service.login(authUserLoginDto);
        return ResponseEntity.ok(new DataDTO<>(sessionDTO));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> accountActivation(String email,
                                                           String verificationCode) {
        Long authUserId = service.accountActivation(email, verificationCode);
        return ResponseEntity.ok(new DataDTO<>(authUserId));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> forgetPassword(String email) {
        Long authUserId = service.forgetPassword(email);
        return ResponseEntity.ok(new DataDTO<>(authUserId));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> restartPassword(String email,
                                                         String verificationCode,
                                                         String newPassword) {
        Long userId = service.restartPassword(email, verificationCode, newPassword);
        return ResponseEntity.ok(new DataDTO<>(userId));
    }

    @Override
    public ResponseEntity<DataDTO<PaginationDTO<List<AuthUserDto>>>> getList(int page,
                                                                             int size) {
        PaginationDTO<List<AuthUserDto>> paginationDTO = service.getList(page, size);
        return ResponseEntity.ok(new DataDTO<>(paginationDTO));
    }

    @Override
    public ResponseEntity<DataDTO<List<AuthUserDto>>> getAll() {
        List<AuthUserDto> userDtoList = service.getAll();
        return ResponseEntity.ok(new DataDTO<>(userDtoList));
    }

    @Override
    public ResponseEntity<DataDTO<AuthUserDto>> getById(Long id) {
        AuthUserDto authUserDto = service.getById(id);
        return ResponseEntity.ok(new DataDTO<>(authUserDto));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> update(AuthUserUpdateDto updateDto) {
        Long id = service.update(updateDto);
        return ResponseEntity.ok(new DataDTO<>(id));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> delete(Long id) {
        Long deleted = service.delete(id);
        return ResponseEntity.ok(new DataDTO<>(deleted));
    }
}