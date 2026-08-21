package com.naderaria.commoncore.dto.request;

public record PaginationDto(
        int pageNumber,
        int pageSize,
        boolean sortAscending,
        String[] sortParams) {
}