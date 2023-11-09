package uz.kuvondikov.clickup.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.kuvondikov.clickup.dto.PaginationDTO;
import uz.kuvondikov.clickup.dto.user.*;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.enums.SystemRole;
import uz.kuvondikov.clickup.exception.BadRequestException;
import uz.kuvondikov.clickup.exception.NotFoundException;
import uz.kuvondikov.clickup.mapper.AuthUserMapper;
import uz.kuvondikov.clickup.repository.AuthUserRepository;
import uz.kuvondikov.clickup.security.jwt.JwtService;
import uz.kuvondikov.clickup.service.AuthUserService;
import uz.kuvondikov.clickup.service.EmailService;
import uz.kuvondikov.clickup.service.base.AbstractService;

import java.util.List;
import java.util.Random;

import static uz.kuvondikov.clickup.constant.ErrorMessages.*;

@Service
public class AuthUserServiceImpl extends AbstractService<AuthUserRepository, AuthUserMapper> implements AuthUserService {

    private static final Random random = new Random();
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthUserServiceImpl(AuthUserRepository repository, AuthUserMapper mapper, PasswordEncoder passwordEncoder, EmailService emailService, AuthenticationManager authenticationManager, JwtService jwtService) {
        super(repository, mapper);
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    private static String generatedVerificationCode() {
        int min = 1000;
        int max = 9999;
        int randomNum = random.nextInt(max - min + 1) + min;
        return "" + randomNum;
    }

    @Override
    public Long register(AuthUserRegisterDto userRegisterDto) {

        if (repository.existsByEmailAndDeletedFalse(userRegisterDto.getEmail()))
            throw new BadRequestException(AUTH_USER_EMAIL_ALREADY_EXISTS);

        userRegisterDto.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        AuthUser authRegisterUser = mapper.fromRegisterDTO(userRegisterDto);
        authRegisterUser.setSystemRole(SystemRole.SYSTEM_ROLE_USER);
        String verificationCode = generatedVerificationCode();
        authRegisterUser.setVerificationCode(verificationCode);
        authRegisterUser.setAccountNonLocked(true);
        authRegisterUser.setAccountNonExpired(true);
        authRegisterUser.setCredentialsNonExpired(true);
        AuthUser savedUser = repository.save(authRegisterUser);

        emailService.sendActivationAccountMessage(userRegisterDto.getEmail(), verificationCode);
        return savedUser.getId();
    }

    @Override
    public SessionDTO login(AuthUserLoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        AuthUser authUser = repository.findByEmailAndDeletedFalse(loginDto.getEmail())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + loginDto.getEmail()));
        return jwtService.createSessionDTO(authUser);
    }

    @Override
    public AuthUserDto getById(Long id) {
        AuthUser authUser = repository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        return mapper.toDTO(authUser);
    }

    @Override
    public List<AuthUserDto> getAll() {
        List<AuthUser> authUserList = repository.findAll();
        return mapper.toDTO(authUserList);
    }

    @Override
    public PaginationDTO<List<AuthUserDto>> getList(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "firstname", "lastname", "email");
        Page<AuthUser> userPage = repository.findAll(pageRequest);
        int totalPages = userPage.getTotalPages();
        List<AuthUser> content = userPage.getContent();
        long totalElements = userPage.getTotalElements();
        List<AuthUserDto> authUserDtoList = mapper.toDTO(content);
        return new PaginationDTO<>(authUserDtoList, page, size, totalElements, totalPages);
    }

    @Override
    public Long update(AuthUserUpdateDto updateDto) {
        AuthUser authUser = repository.findById(updateDto.getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + updateDto.getId()));
        AuthUser fromUpdateDTO = mapper.fromUpdateDTO(updateDto, authUser);
        return repository.save(fromUpdateDTO).getId();
    }

    @Override
    public Long delete(Long id) {
        AuthUser authUser = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        authUser.setDeleted(true);
        repository.save(authUser);
        return id;
    }

    @Override
    public Long accountActivation(String email, String verificationCode) {
        AuthUser authUser = repository.findByEmailAndDeletedFalse(email).orElseThrow(() -> new BadRequestException(WRONG_EMAIL_OR_VERIFICATION_CODE));
        if (!verificationCode.equals(authUser.getVerificationCode()))
            throw new BadRequestException(WRONG_EMAIL_OR_VERIFICATION_CODE);
        authUser.setEnabled(true);
        authUser.setVerificationCode(null);
        repository.save(authUser);
        return authUser.getId();
    }

    @Override
    public Long forgetPassword(String email) {
        AuthUser authUser = repository.findAuthUsersByEmailAndDeletedFalse(email).orElseThrow(() -> new BadRequestException(WRONG_EMAIL_OR_VERIFICATION_CODE));
        String verificationCode = generatedVerificationCode();
        authUser.setVerificationCode(verificationCode);
        repository.save(authUser);
        emailService.sendForgetPasswordMessage(email, verificationCode);
        return authUser.getId();
    }

    @Override
    public Long restartPassword(String email, String verificationCode, String newPassword) {
        AuthUser authUser = repository.findByEmailAndDeletedFalse(email).orElseThrow(() -> new BadRequestException(WRONG_EMAIL_OR_VERIFICATION_CODE));
        if (!verificationCode.equals(authUser.getVerificationCode()))
            throw new BadRequestException(WRONG_EMAIL_OR_VERIFICATION_CODE);
        authUser.setPassword(passwordEncoder.encode(newPassword));
        authUser.setVerificationCode(null);
        repository.save(authUser);
        return authUser.getId();
    }
}
