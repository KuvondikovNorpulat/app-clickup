package uz.kuvondikov.clickup.entity;

import uz.kuvondikov.clickup.entity.template.AbsLongIdEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
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
