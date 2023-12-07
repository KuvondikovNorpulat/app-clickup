package uz.kuvondikov.clickup.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor

public enum WorkspacePermissionName {

    VIEW_SPACE("View space", "Allows users to view the entire space", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER, WorkspaceRoleName.WORKSPACE_ROLE_GUEST)),
    EDIT_SPACE("Edit Space", "Grants permission to edit the space settings", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)),
    MANAGE_SPACE("Manage space", "Provides full control over the space, including adding or removing", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)),
    VIEW_FOLDER("View folder", "Permits users to view the folder and its contents", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    EDIT_FOLDER("Edit folder", "Allows users to edit folder details", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)),
    CREATE_TASK_IN_FOLDER("Create task in folder", "Grants the ability to create tasks within the folder", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    VIEW_LIST("View list", "Allows users to view the list", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    EDIT_LIST("Edit list", "Permits users to edit list details", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)),
    VIEW_TASK("View task", "Allows users to view task details", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    EDIT_TASK("Edit task", "Grants permission to edit task details", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)),
    DELETE_TASK("Delete task", "Allows users to delete tasks", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)),
    MOVE_TASK("Move task", "Permits users to move tasks between lists or folders", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)),
    ADD_COMMENT("Add comment", "Allows users to add comments to tasks", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    DELETE_COMMENT("Delete comment", "Permits users to delete their own comments", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    EDIT_COMMENT("Edit comment", "Grants the ability to edit their own comments", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    ADD_ATTACHMENT("Add attachment", "Allows users to add attachments to tasks", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    DELETE_ATTACHMENT("Delete attachment", "Permits users to delete their own attachments", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    EDIT_ATTACHMENT("Edit attachment", "Grants the ability to edit their own attachments", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    ADD_SUBTASK("Add subtask", "Allows users to add subtasks to tasks", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    DELETE_SUBTASK("Delete subtask", "Permits users to delete their own subtasks", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    EDIT_SUBTASK("Edit subtask", "Grants the ability to edit their own subtasks", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    TRACK_TIME("Track time", "Allows users to track time on tasks", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    VIEW_TIME("View time", "Permits users to view time tracked by others", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    VIEW_BOARD("View board", "Allows users to view boards", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    EDIT_BOARD("Edit board", "Grants permission to edit board details", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)),
    VIEW_DOCUMENT("View document", "Allows users to view documents", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    EDIT_DOCUMENT("Edit document", "Permits users to edit document details", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)),
    VIEW_GOAL("View goal", "Allows users to view goals", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    EDIT_GOAL("Edit goal", "Grants permission to edit goal details", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)),
    EDIT_CUSTOM_FIELD("Edit custom field", "Permits users to edit custom field values", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    CREATE_AUTOMATION("Create automation", "Allows users to create automations", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)),
    VIEW_REPORTS("View reports", "Allows users to view reports", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN, WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)),
    MANAGE_INTEGRATIONS("Manage integrations", "Grants permission to manage integrations", Arrays.asList(WorkspaceRoleName.WORKSPACE_ROLE_OWNER, WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)),
    MANAGE_WORKSPACE("Manage workspace", "Provides full control over workspace settings", List.of(WorkspaceRoleName.WORKSPACE_ROLE_OWNER));

    public final String title;
    public final String description;
    public final List<WorkspaceRoleName> workspaceRoleNames;
}
