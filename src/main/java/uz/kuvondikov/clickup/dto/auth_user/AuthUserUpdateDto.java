package uz.kuvondikov.clickup.dto.auth_user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.kuvondikov.clickup.entity.Attachment;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.enums.Color;

import java.io.Serializable;

/**
 * DTO for {@link AuthUser}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AuthUserUpdateDto implements Serializable {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String password;
    private Color color;
    private Attachment attachment;
}