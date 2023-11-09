package uz.kuvondikov.clickup.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.kuvondikov.clickup.entity.Attachment;
import uz.kuvondikov.clickup.enums.Color;
import uz.kuvondikov.clickup.enums.SystemRole;

import java.io.Serializable;

/**
 * DTO for {@link uz.kuvondikov.clickup.entity.AuthUser}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AuthUserRegisterDto implements Serializable {
    @NotBlank
    String password;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @Email
    private String email;
    private Color color;

    private @NotNull
    Attachment attachment;
}