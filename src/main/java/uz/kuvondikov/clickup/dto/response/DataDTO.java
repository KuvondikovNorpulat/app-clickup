package uz.kuvondikov.clickup.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataDTO<T> implements Serializable {

    private T data;
    private AppErrorDTO error;
    private boolean success;
    private Long totalCount;

    public DataDTO(boolean success) {
        this.success = success;
    }

    public DataDTO(T data) {
        this.data = data;
        this.success = true;
    }

    public DataDTO(AppErrorDTO error) {
        this.error = error;
        this.success = false;
    }

    public DataDTO(T data, Long totalCount) {
        this.data = data;
        this.success = true;
        this.totalCount = totalCount;
    }
}
