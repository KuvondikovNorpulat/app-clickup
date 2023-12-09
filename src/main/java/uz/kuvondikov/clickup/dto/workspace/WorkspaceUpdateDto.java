package uz.kuvondikov.clickup.dto.workspace;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class WorkspaceUpdateDto implements Serializable {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private Color color;
    private Attachment avatar;
}