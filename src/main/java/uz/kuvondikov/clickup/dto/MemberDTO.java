package uz.kuvondikov.clickup.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class MemberDTO {
    @NotBlank
    private String newMemberEmail;
    private Long workspaceId;
    private Long workspaceRoleId;
}
