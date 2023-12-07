package uz.kuvondikov.clickup.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor

public enum WorkspacePermissionName {
    CAN_ADD_REMOVE("Add/Remove Members", "Remove and added members", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)),
    CAN_EDIT("Edit Members", "Edite members", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_OWNER));

    public final String title;
    public final String description;
    public final List<WorkspaceRoleName> workspaceRoleNames;
}
