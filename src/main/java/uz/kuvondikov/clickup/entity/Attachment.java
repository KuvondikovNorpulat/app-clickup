package uz.kuvondikov.clickup.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import uz.kuvondikov.clickup.entity.template.AbsLongIdEntity;
import uz.kuvondikov.clickup.entity.template.AbsMainEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Attachment extends AbsLongIdEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private Long size;

    private MediaType mediaType;

}
