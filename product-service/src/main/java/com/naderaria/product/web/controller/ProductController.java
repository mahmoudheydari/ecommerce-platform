package com.naderaria.product.web.controller;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commoncore.util.WebUtil;
import com.naderaria.product.application.service.ProductService;
import com.naderaria.product.web.dto.request.ReqProductDto;
import com.naderaria.product.web.dto.request.ReqUpdatableProductDto;
import com.naderaria.product.web.dto.response.ResProductDto;
import com.naderaria.product.web.dto.response.ResProductPageItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ecom")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    @PreAuthorize("hasRole('Admin') and hasPermission('Product','read')")
    public ResponseEntity<PageResponse<ResProductPageItemDto>> getAllProduct(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "30") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortAscending", defaultValue = "true") boolean sortAscending) {

        PaginationDto paginationDto = new PaginationDto(pageNumber,pageSize,sortAscending,new String[]{sortBy});
        return ResponseEntity.ok(productService.getProducts(paginationDto));
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("hasRole('Admin') and hasPermission('Product','read')")
    public ResponseEntity<ResProductDto> getProduct(@PathVariable("id") long id) {
        ResProductDto resProductDto = productService.getProduct(id);
        return ResponseEntity.ok(resProductDto);
    }

    @PostMapping("/product")
    @PreAuthorize("hasRole('Admin') and hasPermission('Product','write')")
    public ResponseEntity<ResProductDto> save(@Validated @RequestBody ReqProductDto reqProductDto) {
        ResProductDto resProductDto = productService.save(reqProductDto);
        return ResponseEntity.created(WebUtil.createURI("/ecom/product/", resProductDto.id())).body(resProductDto);
    }

    @PutMapping("/product")
    @PreAuthorize("hasRole('Admin') and hasPermission('Product','update')")
    public ResponseEntity<Void> update(@Validated @RequestBody ReqUpdatableProductDto reqUpdatableProductDto) {
        productService.update(reqUpdatableProductDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasRole('Admin') and hasPermission('Product','delete')")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}