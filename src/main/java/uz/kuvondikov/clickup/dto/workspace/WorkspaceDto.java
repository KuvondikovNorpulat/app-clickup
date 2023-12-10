package uz.kuvondikov.clickup.dto.workspace;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
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
@Builder

public class WorkspaceDto implements Serializable {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private Color color;
    @NotNull
    private AuthUserDto owner;
    private Attachment avatar;
}