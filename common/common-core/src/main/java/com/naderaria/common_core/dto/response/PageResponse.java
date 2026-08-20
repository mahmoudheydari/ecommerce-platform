package com.naderaria.common_core.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PageResponse<R extends PageItem> {

    private List<R> items;
    private Integer page;
    private Integer totalPages;
    private Long totalElements;

}
