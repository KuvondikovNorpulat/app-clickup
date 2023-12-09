package uz.kuvondikov.clickup.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.kuvondikov.clickup.dto.PaginationDTO;
import uz.kuvondikov.clickup.dto.auth_user.*;
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

        if (repository.existsByEmailAndDeletedFalseAndEnabledTrue(userRegisterDto.getEmail()))
            throw new BadRequestException(AUTH_USER_EMAIL_ALREADY_EXISTS + userRegisterDto.getEmail());

        if (repository.existsByEmailAndDeletedFalseAndEnabledFalse(userRegisterDto.getEmail())) {
            return oldProfileRegister(userRegisterDto);
        } else {
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
    }

    private Long oldProfileRegister(AuthUserRegisterDto userRegisterDto) {
        AuthUser authUser = repository.findByEmailAndDeletedFalseAndEnabledFalse(userRegisterDto.getEmail()).orElseThrow();
        authUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        authUser.setColor(userRegisterDto.getColor());
        emailService.sendActivationAccountMessage(authUser.getEmail(), authUser.getVerificationCode());
        repository.save(authUser);
        return authUser.getId();
    }

    @Override
    public SessionDTO login(AuthUserLoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        AuthUser authUser = repository.findByEmailAndDeletedFalseAndEnabledTrue(loginDto.getEmail()).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + loginDto.getEmail()));
        return jwtService.createSessionDTO(authUser);
    }

    @Override
    public AuthUserDto getById(Long id) {
        AuthUser authUser = repository.findById(id).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + id));
        return mapper.toDTO(authUser);
    }

    @Override
    public List<AuthUserDto> getAll() {
        List<AuthUser> authUserList = repository.findAllByDeletedFalseAndEnabledTrue();
        return mapper.toDTO(authUserList);
    }

    @Override
    public PaginationDTO<List<AuthUserDto>> getList(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "firstname", "lastname", "email");
        Page<AuthUser> userPage = repository.findByDeletedFalseAndEnabledTrue(pageRequest);
        int totalPages = userPage.getTotalPages();
        List<AuthUser> content = userPage.getContent();
        long totalElements = userPage.getTotalElements();
        List<AuthUserDto> authUserDtoList = mapper.toDTO(content);
        return new PaginationDTO<>(authUserDtoList, page, size, totalElements, totalPages);
    }

    @Override
    public Long update(AuthUserUpdateDto updateDto, AuthUser authUser) {

        String password = updateDto.getPassword();
        if (!password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,}$"))
            throw new BadRequestException(NOT_STRENGTH_PASSWORD);

        AuthUser fromUpdateDTO = mapper.fromUpdateDTO(updateDto, authUser);
        fromUpdateDTO.setId(authUser.getId());
        return repository.save(fromUpdateDTO).getId();
    }

    @Override
    public Long delete(Long id) {
        AuthUser authUser = repository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + id));
        authUser.setDeleted(true);
        repository.save(authUser);
        return id;
    }

    @Override
    public Long accountActivation(String email, String verificationCode) {
        AuthUser authUser = repository.findByEmailAndDeletedFalseAndEnabledFalse(email).orElseThrow(() -> new BadRequestException(WRONG_EMAIL_OR_VERIFICATION_CODE));
        if (!verificationCode.equals(authUser.getVerificationCode()))
            throw new BadRequestException(WRONG_EMAIL_OR_VERIFICATION_CODE);
        authUser.setEnabled(true);
        authUser.setVerificationCode(null);
        repository.save(authUser);
        return authUser.getId();
    }

    @Override
    public Long forgetPassword(String email) {
        AuthUser authUser = repository.findByEmailAndDeletedFalseAndEnabledTrue(email).orElseThrow(() -> new BadRequestException(WRONG_EMAIL_OR_VERIFICATION_CODE));
        String verificationCode = generatedVerificationCode();
        authUser.setVerificationCode(verificationCode);
        repository.save(authUser);
        emailService.sendForgetPasswordMessage(email, verificationCode);
        return authUser.getId();
    }

    @Override
    public Long restartPassword(String email, String verificationCode, String newPassword) {
        AuthUser authUser = repository.findByEmailAndDeletedFalseAndEnabledTrue(email).orElseThrow(() -> new BadRequestException(WRONG_EMAIL_OR_VERIFICATION_CODE));
        if (!verificationCode.equals(authUser.getVerificationCode()))
            throw new BadRequestException(WRONG_EMAIL_OR_VERIFICATION_CODE);
        authUser.setPassword(passwordEncoder.encode(newPassword));
        authUser.setVerificationCode(null);
        repository.save(authUser);
        return authUser.getId();
    }

}