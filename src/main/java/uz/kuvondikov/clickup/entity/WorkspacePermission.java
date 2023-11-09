package uz.kuvondikov.clickup.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.kuvondikov.clickup.entity.template.AbsLongIdEntity;
import uz.kuvondikov.clickup.enums.Permission;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class WorkspacePermission extends AbsLongIdEntity {
    @ManyToOne(optional = false)
    private WorkspaceRole workspaceRole;//orinbosar

    @Enumerated(EnumType.STRING)
    private Permission permission;//add,remove,bla...

}
