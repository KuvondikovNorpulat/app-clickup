package uz.kuvondikov.clickup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class MemberEditDTO {
    private Long userId;
    private Long workspaceId;
    private Long workspaceRoleId;
}
