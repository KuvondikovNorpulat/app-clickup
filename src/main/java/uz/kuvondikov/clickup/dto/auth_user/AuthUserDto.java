package uz.kuvondikov.clickup.dto.auth_user;

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

public class AuthUserDto implements Serializable {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private Color color;

    private Attachment attachment;

    private SystemRole systemRole;
}