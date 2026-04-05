package com.alibou.saas.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;

    public static <T> PageResponse<T> of(final Page<T> page) {
        return PageResponse.<T>builder()
                           .content(page.getContent())
                           .page(page.getNumber())
                           .size(page.getSize())
                           .totalElements(page.getTotalElements())
                           .totalPages(page.getTotalPages())
                           .hasNext(page.hasNext())
                           .hasPrevious(page.hasPrevious())
                           .isFirst(page.isFirst())
                           .isLast(page.isLast())
                           .build();
    }

}
