package com.naderaria.product.application.service;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commoncore.exception.BusinessException;
import com.naderaria.commoncore.exception.ErrorCode;
import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.product.application.mapper.CategoryMapper;
import com.naderaria.product.domain.entity.Category;
import com.naderaria.product.domain.repository.CategoryRepository;
import com.naderaria.product.domain.repository.ProductRepository;
import com.naderaria.product.web.dto.request.ReqCategoryDto;
import com.naderaria.product.web.dto.request.ReqUpdatableCategoryDto;
import com.naderaria.product.web.dto.response.ResCategoryDto;
import com.naderaria.product.web.dto.response.ResCategoryPageItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public PageResponse<ResCategoryPageItemDto> getCategories(PaginationDto reqBasePaginationDto) {
        Pageable pageable = PageConvertor.convertToPageable(reqBasePaginationDto);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryMapper.toReCategoriesPageItemDto(categoryPage);
    }

    @Override
    @Transactional
    public ResCategoryDto getCategory(Long id) {
        return categoryMapper.toResCategoryDto(
                categoryRepository.findById(id).orElseThrow(()-> BusinessException.of(ErrorCode.CategoryNotFoundException))
        );
    }

    @Override
    @Transactional
    public ResCategoryDto save(ReqCategoryDto reqCategoryDto) {
        Category category = categoryMapper.toCategory(reqCategoryDto);
        if(reqCategoryDto.parentId() != null) {
            Category parent = categoryRepository.findById(reqCategoryDto.parentId()).orElseThrow(()-> BusinessException.of(ErrorCode.CategoryNotFoundException));
            category.changeParent(parent);
        }
        categoryRepository.save(category);
        return categoryMapper.toResCategoryDto(category);
    }

    @Override
    @Transactional
    public void update(ReqUpdatableCategoryDto reqUpdatableCategoryDto) {
        Category oldCategory = categoryRepository.findById(reqUpdatableCategoryDto.id())
                .orElseThrow(()-> BusinessException.of(ErrorCode.CategoryNotFoundException));
        categoryMapper.update(reqUpdatableCategoryDto, oldCategory);
    }

    @Override
    @Transactional
    public void delete(long id) {
        if(productRepository.existsByCategoryId(id)) throw new BusinessException(ErrorCode.CategoryInUseException);
        categoryRepository.deleteById(id);
    }
}