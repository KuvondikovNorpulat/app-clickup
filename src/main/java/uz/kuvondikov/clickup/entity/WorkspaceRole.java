package uz.kuvondikov.clickup.entity;

import uz.kuvondikov.clickup.entity.template.AbsLongIdEntity;
import uz.kuvondikov.clickup.enums.WorkspaceRoleName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkspaceRole extends AbsLongIdEntity {

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Workspace workspace;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private WorkspaceRoleName extendsWorkspaceRoleName;

}
