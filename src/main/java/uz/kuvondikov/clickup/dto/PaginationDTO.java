package uz.kuvondikov.clickup.dto;

import lombok.Data;

@Data
public class PaginationDTO<T> {

    private T content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;



    public PaginationDTO(T content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
