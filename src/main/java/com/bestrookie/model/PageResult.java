package com.bestrookie.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @author : bestrookie
 * @date : 19:12 2020/10/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult {
    private int pageNumber;
    private int pageSize;
    private long totalSize;
    private int totalPages;
    private List<?> content;
}
