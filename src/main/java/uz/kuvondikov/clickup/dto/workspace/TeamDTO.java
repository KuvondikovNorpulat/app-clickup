package uz.kuvondikov.clickup.dto.workspace;

import lombok.*;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.entity.WorkspaceUser;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String roleName;
    private LocalDateTime lastActive;

    public static TeamDTO from(WorkspaceUser workspaceUser) {
        AuthUser user = workspaceUser.getUser();
        return TeamDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .email(user.getEmail())
                .roleName(workspaceUser.getWorkspaceRole().getName())
                .lastActive(user.getLastActive())
                .build();
    }
}
