package uz.kuvondikov.clickup.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.kuvondikov.clickup.dto.PaginationDTO;
import uz.kuvondikov.clickup.dto.user.*;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.enums.SystemRole;
import uz.kuvondikov.clickup.exception.BadRequestException;
import uz.kuvondikov.clickup.mapper.AuthUserMapper;
import uz.kuvondikov.clickup.repository.AuthUserRepository;
import uz.kuvondikov.clickup.service.AuthUserService;
import uz.kuvondikov.clickup.service.EmailService;
import uz.kuvondikov.clickup.service.base.AbstractService;

import java.util.List;
import java.util.Random;

import static uz.kuvondikov.clickup.constant.ErrorMessages.AUTH_USER_EMAIL_ALREADY_EXISTS;

@Service
public class AuthUserServiceImpl extends AbstractService<AuthUserRepository, AuthUserMapper> implements AuthUserService {

    private static final Random random = new Random();
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthUserServiceImpl(AuthUserRepository repository, AuthUserMapper mapper, PasswordEncoder passwordEncoder, EmailService emailService) {
        super(repository, mapper);
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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
        AuthUser savedUser = repository.save(authRegisterUser);

        emailService.sendEmailMessage(userRegisterDto.getEmail(), verificationCode);
        return savedUser.getId();
    }

    @Override
    public SessionDTO login(AuthUserLoginDto loginDto) {
        return null;
    }

    @Override
    public AuthUserDto getById(Long id) {
        return null;
    }

    @Override
    public List<AuthUserDto> getAll() {
        return null;
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
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }

}
