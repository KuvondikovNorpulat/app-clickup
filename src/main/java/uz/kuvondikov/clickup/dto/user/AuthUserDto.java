package uz.kuvondikov.clickup.dto.user;

import lombok.*;
import uz.kuvondikov.clickup.entity.Attachment;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.enums.Color;
import uz.kuvondikov.clickup.enums.SystemRole;

import java.io.Serializable;
import java.sql.Timestamp;

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

    private Timestamp creatAt;

    private Timestamp updateAt;

    private AuthUser updatedBy;

    private AuthUser createdBy;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private Color color;

    private Attachment attachment;

    private String mailCode;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    private boolean deleted;

    private SystemRole systemRole;
}