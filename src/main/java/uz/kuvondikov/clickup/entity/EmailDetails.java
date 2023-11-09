package uz.kuvondikov.clickup.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class EmailDetails {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
