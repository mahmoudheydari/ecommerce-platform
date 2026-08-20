package dto.request;

public record PaginationDto(
        int pageNumber,
        int pageSize,
        boolean sortAscending,
        String[] sortParams) {
}