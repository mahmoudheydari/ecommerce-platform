package com.naderaria.common_data.util;

import com.naderaria.common_core.dto.request.PaginationDto;
import com.naderaria.common_core.dto.response.PageItem;
import com.naderaria.common_core.dto.response.PageResponse;
import com.naderaria.common_data.domain.BaseEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;

public interface PageConvertor {

    static Pageable convertToPageable(PaginationDto paginationDto) {
        return PageRequest.of((paginationDto.pageNumber() <= 0 ? 0 : paginationDto.pageNumber() - 1),
                paginationDto.pageSize(), createSort(paginationDto.sortAscending(), paginationDto.sortParams()));
    }


    static <R extends PageItem, E extends BaseEntity> PageResponse<R>
    toPageableDto(Page<E> pageEntities, Function<E, R> convertor) {

        List<R> pageItems = pageEntities.getContent().stream().map(convertor).toList();
        return new PageResponse<>(
                pageItems,
                pageEntities.getPageable().getPageNumber(),
                pageEntities.getTotalPages(),
                pageEntities.getTotalElements()
        );
    }

    static <R extends PageItem> PageResponse<R> pageDtoListToPageableDto(Page<R> pageDtoList) {
        List<R> pageItems = pageDtoList.getContent().stream().toList();
        return new PageResponse<>(
                pageItems,
                pageDtoList.getPageable().getPageNumber(),
                pageDtoList.getTotalPages(),
                pageDtoList.getTotalElements()
        );
    }

    static Sort createSort(boolean isSortAscending, String... sortParams) {
        Sort.Direction direction = (isSortAscending ? Sort.Direction.ASC : Sort.Direction.DESC);
        return (sortParams != null && sortParams.length != 0) ?
                Sort.by(direction, sortParams) :
                Sort.by(direction, "id");

    }

}
