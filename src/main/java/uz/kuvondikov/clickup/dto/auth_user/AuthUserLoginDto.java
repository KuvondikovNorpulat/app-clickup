package uz.kuvondikov.clickup.dto.auth_user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link uz.kuvondikov.clickup.entity.AuthUser}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AuthUserLoginDto implements Serializable {
    @Email
    private String email;

    @NotBlank
    private String password;
}