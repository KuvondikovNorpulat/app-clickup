package uz.kuvondikov.clickup.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.kuvondikov.clickup.entity.template.AbsMainEntity;
import uz.kuvondikov.clickup.enums.Color;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Workspace extends AbsMainEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AuthUser owner;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment avatar;

    private boolean deleted;
}
