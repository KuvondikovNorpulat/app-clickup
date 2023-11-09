package uz.kuvondikov.clickup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kuvondikov.clickup.controller.base.AbstractController;
import uz.kuvondikov.clickup.dto.PaginationDTO;
import uz.kuvondikov.clickup.dto.response.DataDTO;
import uz.kuvondikov.clickup.dto.user.*;

import java.util.List;

@RequestMapping(path = AbstractController.PATH + "/auth")
public interface AuthController {

    @PostMapping("/register")
    ResponseEntity<DataDTO<Long>> register(@RequestBody AuthUserRegisterDto userRegisterDto);

    @GetMapping("/active")
    ResponseEntity<DataDTO<Long>> accountActivation(@RequestParam String email,
                                                    @RequestParam String verificationCode);

    @PostMapping("/login")
    ResponseEntity<DataDTO<SessionDTO>> login(@RequestBody AuthUserLoginDto authUserLoginDto);

    @PostMapping("/forget-password")
    ResponseEntity<DataDTO<Long>> forgetPassword(@RequestBody String email);

    @PostMapping("/restart-password")
    ResponseEntity<DataDTO<Long>> restartPassword(@RequestParam String email,
                                                  @RequestParam String verificationCode,
                                                  @RequestBody String newPassword);

    @GetMapping("/get-page")
    ResponseEntity<DataDTO<PaginationDTO<List<AuthUserDto>>>> getList(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size);

    @GetMapping("/get-all")
    ResponseEntity<DataDTO<List<AuthUserDto>>> getAll();

    @GetMapping("/get/{id}")
    ResponseEntity<DataDTO<AuthUserDto>> getById(@PathVariable Long id);

    @PutMapping("/update")
    ResponseEntity<DataDTO<Long>> update(@RequestBody AuthUserUpdateDto updateDto);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<DataDTO<Long>> delete(@PathVariable Long id);
}
