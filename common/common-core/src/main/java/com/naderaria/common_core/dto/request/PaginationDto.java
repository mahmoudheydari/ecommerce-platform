package com.naderaria.common_core.dto.request;

public record PaginationDto(
        int pageNumber,
        int pageSize,
        boolean sortAscending,
        String[] sortParams) {
}