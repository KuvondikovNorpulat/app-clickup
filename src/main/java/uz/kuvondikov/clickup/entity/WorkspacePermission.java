package uz.kuvondikov.clickup.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.kuvondikov.clickup.entity.template.AbsLongIdEntity;
import uz.kuvondikov.clickup.enums.WorkspacePermissionName;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class WorkspacePermission extends AbsLongIdEntity {
    @ManyToOne(optional = false)
    private WorkspaceRole workspaceRole;

    @Enumerated(EnumType.STRING)
    private WorkspacePermissionName workspacePermissionName;//add,remove,bla...

}
