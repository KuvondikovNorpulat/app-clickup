package uz.kuvondikov.clickup.entity.template;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.kuvondikov.clickup.entity.AuthUser;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbsMainEntity extends AbsLongIdEntity {

    @Column(nullable = false, updatable = false)
    @CreationTimestamp()
    private Timestamp creatAt;

    @UpdateTimestamp
    private Timestamp updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private AuthUser updatedBy;

    @JoinColumn(updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private AuthUser createdBy;
}
