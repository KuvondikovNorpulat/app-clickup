package uz.kuvondikov.clickup.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.kuvondikov.clickup.constant.ErrorMessages;
import uz.kuvondikov.clickup.exception.NotFoundException;
import uz.kuvondikov.clickup.repository.AuthUserRepository;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthUserRepository authUserRepository;


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) authUserRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.USER_NOT_FOUND + username));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
