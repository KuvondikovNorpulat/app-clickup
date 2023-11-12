package uz.kuvondikov.clickup.dto.workspace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.kuvondikov.clickup.dto.auth_user.AuthUserDto;
import uz.kuvondikov.clickup.entity.Attachment;
import uz.kuvondikov.clickup.enums.Color;

import java.io.Serializable;

/**
 * DTO for {@link uz.kuvondikov.clickup.entity.Workspace}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class WorkspaceDto implements Serializable {
    private Long id;
    private String name;
    private Color color;
    private AuthUserDto owner;
    private Attachment avatar;
}