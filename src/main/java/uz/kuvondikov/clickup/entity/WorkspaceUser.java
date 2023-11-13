package uz.kuvondikov.clickup.entity;

import lombok.*;
import uz.kuvondikov.clickup.entity.template.AbsLongIdEntity;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkspaceUser extends AbsLongIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private AuthUser user;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private WorkspaceRole workspaceRole;

    @Column(nullable = false)
    private Timestamp invitedAt;

    private Timestamp joinedAt;
}
