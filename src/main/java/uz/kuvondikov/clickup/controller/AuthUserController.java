package uz.kuvondikov.clickup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kuvondikov.clickup.controller.base.AbstractController;
import uz.kuvondikov.clickup.dto.PaginationDTO;
import uz.kuvondikov.clickup.dto.response.DataDTO;
import uz.kuvondikov.clickup.dto.user.AuthUserDto;
import uz.kuvondikov.clickup.dto.user.AuthUserLoginDto;
import uz.kuvondikov.clickup.dto.user.AuthUserRegisterDto;
import uz.kuvondikov.clickup.dto.user.SessionDTO;
import uz.kuvondikov.clickup.service.AuthUserService;

import java.util.List;

@RestController
@RequestMapping(path = AuthUserController.PATH + "auth/")
public class AuthUserController extends AbstractController<AuthUserService> {
    public AuthUserController(AuthUserService service) {
        super(service);
    }

    @PostMapping("/register")
    public ResponseEntity<DataDTO<Long>> register(@RequestBody AuthUserRegisterDto userRegisterDto) {
        Long registerUserId = service.register(userRegisterDto);
        return ResponseEntity.ok(new DataDTO<>(registerUserId));
    }

    @PostMapping("/login")
    public ResponseEntity<DataDTO<SessionDTO>> login(@RequestBody AuthUserLoginDto authUserLoginDto) {
        SessionDTO sessionDTO = service.login(authUserLoginDto);
        return ResponseEntity.ok(new DataDTO<>(sessionDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<DataDTO<PaginationDTO<List<AuthUserDto>>>> getList(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size) {
        return null;
    }
}