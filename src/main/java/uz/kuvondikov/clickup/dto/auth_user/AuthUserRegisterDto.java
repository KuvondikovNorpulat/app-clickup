package uz.kuvondikov.clickup.dto.auth_user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.kuvondikov.clickup.enums.Color;

/**
 * DTO for {@link uz.kuvondikov.clickup.entity.AuthUser}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AuthUserRegisterDto {

    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private Color color;
}