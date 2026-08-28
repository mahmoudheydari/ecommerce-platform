package com.naderaria.product.application.service;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commoncore.exception.BusinessException;
import com.naderaria.commoncore.exception.ErrorCode;
import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.product.application.mapper.ProductMapper;
import com.naderaria.product.domain.entity.Product;
import com.naderaria.product.domain.repository.ProductRepository;
import com.naderaria.product.web.dto.intrernal.ProductPriceDto;
import com.naderaria.product.web.dto.request.ReqProductDto;
import com.naderaria.product.web.dto.request.ReqUpdatableProductDto;
import com.naderaria.product.web.dto.response.ResProductDto;
import com.naderaria.product.web.dto.response.ResProductPageItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public PageResponse<ResProductPageItemDto> getProducts(PaginationDto paginationDto) {
        Pageable pageable = PageConvertor.convertToPageable(paginationDto);
        Page<ResProductPageItemDto> resProductPageItemDtoList = productRepository.findAllProducts(pageable);
        return productMapper.toResProductPageItemDto(resProductPageItemDtoList);
    }

    @Override
    @Transactional
    public ResProductDto getProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> BusinessException.of(ErrorCode.ProductNotFoundException));
        return productMapper.toResProductDto(product);
    }

    @Override
    @Transactional
    public ResProductDto save(ReqProductDto reqProductDto) {
        Product product = productMapper.toProduct(reqProductDto);
        productRepository.save(product);
        return productMapper.toResProductDto(product);
    }

    @Override
    @Transactional
    public void update(ReqUpdatableProductDto reqUpdatableProductDto) {
        Product oldProduct = productRepository.findById(reqUpdatableProductDto.id()).orElseThrow(()-> BusinessException.of(ErrorCode.ProductNotFoundException));
        productMapper.update(reqUpdatableProductDto, oldProduct);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> BusinessException.of(ErrorCode.ProductNotFoundException));
        product.unavailableProduct();
    }

    @Override
    @Transactional
    public ProductPriceDto getFinalPrice(Long id){
        Product product = productRepository.findById(id).orElseThrow(()-> BusinessException.of(ErrorCode.ProductNotFoundException));
        return new ProductPriceDto(product.getId(),product.getFinalPrice());
    }

}