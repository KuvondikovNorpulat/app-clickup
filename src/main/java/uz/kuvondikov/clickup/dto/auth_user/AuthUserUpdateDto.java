package uz.kuvondikov.clickup.dto.auth_user;

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
    private String firstname;
    private String lastname;
    private String password;
    private Color color;
    private Attachment attachment;
}