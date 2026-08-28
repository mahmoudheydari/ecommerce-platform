package com.naderaria.product.web.controller;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commoncore.util.WebUtil;
import com.naderaria.product.application.service.CategoryService;
import com.naderaria.product.web.dto.request.ReqCategoryDto;
import com.naderaria.product.web.dto.request.ReqUpdatableCategoryDto;
import com.naderaria.product.web.dto.response.ResCategoryDto;
import com.naderaria.product.web.dto.response.ResCategoryPageItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ecom")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    @PreAuthorize("hasRole('Admin') and hasPermission('Category','read')")
    public ResponseEntity<PageResponse<ResCategoryPageItemDto>> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "30") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortAscending", defaultValue = "true") boolean sortAscending) {

        PaginationDto paginationDto = new PaginationDto(pageNumber,pageSize,sortAscending,new String[]{sortBy});
        return ResponseEntity.ok(categoryService.getCategories(paginationDto));
    }

    @GetMapping("/category/{id}")
    @PreAuthorize("hasRole('Admin') and hasPermission('Category','read')")
    public ResponseEntity<ResCategoryDto> getCategory(@PathVariable("id") long id) {
        ResCategoryDto resCategoryDto = categoryService.getCategory(id);
        return ResponseEntity.ok(resCategoryDto);
    }

    @PostMapping("/category")
    @PreAuthorize("hasRole('Admin') and hasPermission('Category','write')")
    public ResponseEntity<ResCategoryDto> save(@Validated @RequestBody ReqCategoryDto reqCategoryDto) {
        ResCategoryDto resCategoryDto = categoryService.save(reqCategoryDto);
        return ResponseEntity.created(WebUtil.createURI("/ecom/category/", resCategoryDto.id())).body(resCategoryDto);
    }

    @PutMapping("/category")
    @PreAuthorize("hasRole('Admin') and hasPermission('Category','update')")
    public ResponseEntity<Void> update(@Validated @RequestBody ReqUpdatableCategoryDto reqUpdatableCategoryDto) {
        categoryService.update(reqUpdatableCategoryDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/category/{id}")
    @PreAuthorize("hasRole('Admin') and hasPermission('Category','delete')")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}