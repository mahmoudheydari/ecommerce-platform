package com.naderaria.product.application.service;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commoncore.exception.BusinessException;
import com.naderaria.commoncore.exception.ErrorCode;
import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.product.application.mapper.ProductMapper;
import com.naderaria.product.domain.entity.*;
import com.naderaria.product.domain.repository.ProductRepository;
import com.naderaria.product.web.dto.request.ReqInventoryDto;
import com.naderaria.product.web.dto.request.ReqPriceDto;
import com.naderaria.product.web.dto.request.ReqProductDto;
import com.naderaria.product.web.dto.response.ResInventoryDto;
import com.naderaria.product.web.dto.response.ResPriceDto;
import com.naderaria.product.web.dto.response.ResProductDto;
import com.naderaria.product.web.dto.response.ResProductPageItemDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Category createCellPhoneCategory() {
        return Category.builder()
                .id(1L)
                .name("Cell Phone")
                .active(true)
                .description("Cell Phone Category")
                .sortOrder(1)
                .build();
    }

    private Currency createDollarCurrency() {
        return Currency.builder().id(1L).code("USD").name("USA Dollar").symbol("$").fractionDigits(1).build();
    }

    private Price createPrice(Long id, BigDecimal amount, int discount) {
        return Price.builder().id(id).amount(amount).discount(discount).currency(createDollarCurrency()).build();
    }

    private Inventory createInventory(Long id, int quantity, int reservedQuantity) {
        return Inventory.builder().id(id).quantity(quantity).reservedQuantity(reservedQuantity).build();
    }

    @Test
    void getProducts_whenPaginationDtoInitialized_shouldBeReturnPageResponse() {
        //Arrange
        PaginationDto paginationDto = new PaginationDto(0, 10, true, new String[]{"category"});
        Pageable pageable = PageConvertor.convertToPageable(paginationDto);


        List<ResProductPageItemDto> resProductPageItemDto = List.of(
                new ResProductPageItemDto(1L, "IPhone 14", "IPhone 14 ProMax 256GB", "Apple CellPhone", BigDecimal.valueOf(800), ProductStatusType.OUT_OF_STOCK, 0),
                new ResProductPageItemDto(2L, "IPhone 15", "IPhone 15 ProMax 256GB", "Apple CellPhone", BigDecimal.valueOf(1000), ProductStatusType.ACTIVE, 20),
                new ResProductPageItemDto(3L, "IPhone 16", "IPhone 16 ProMax 256GB", "Apple CellPhone", BigDecimal.valueOf(1200), ProductStatusType.ACTIVE, 500));

        Page<ResProductPageItemDto> resProductPageItemDtoPage = new PageImpl<>(resProductPageItemDto, pageable, resProductPageItemDto.size());
        PageResponse<ResProductPageItemDto> expectedResponse = new PageResponse<>(resProductPageItemDto, 0, 1,
                (long) resProductPageItemDto.size());

        when(productRepository.findAllProducts(pageable)).thenReturn((resProductPageItemDtoPage));
        when(productMapper.toResProductPageItemDto(resProductPageItemDtoPage)).thenReturn(expectedResponse);

        //Act
        PageResponse<ResProductPageItemDto> resultPage = productService.getProducts(paginationDto);

        //Assert
        assertEquals(resultPage.getTotalElements(), resProductPageItemDtoPage.getTotalElements());
        assertEquals(resultPage.getTotalPages(), resProductPageItemDtoPage.getTotalPages());
        assertEquals(resultPage.getPage(), resProductPageItemDtoPage.getNumber());

        verify(productRepository, times(1)).findAllProducts(pageable);
        verify(productMapper, times(1)).toResProductPageItemDto(resProductPageItemDtoPage);


    }


    @Test
    void getProductById_whenProductNotFound_shouldThrowBusinessException() {


        when(productRepository.findById(10L)).thenReturn(Optional.empty());

        BusinessException businessException = assertThrows(BusinessException.class, () -> productService.getProduct(10));

        assertEquals(ErrorCode.ProductNotFoundException, businessException.getErrorCode());

        verify(productRepository, times(1)).findById(10L);
        verifyNoInteractions(productMapper);
    }

    @Test
    void getProductById_whenProductFound_shouldBeReturnProduct() {

        final String productName = "IPhone 14 Pro Max";
        final String productSlug = "IPhone 15 Pro Max 256GB";
        final String productDescription = " Apple IPhone 14 Pro Max 256G - Orange";
        final Category productCategory = createCellPhoneCategory();
        final Price iPhone14Price = createPrice(1L, BigDecimal.valueOf(800L), 0);
        final Inventory iPhone14Inventory = createInventory(1L, 200, 150);

        Product iPhone14product = Product.builder().id(1L).name(productName)
                .slug(productSlug).description(productDescription)
                .category(productCategory).statusType(ProductStatusType.OUT_OF_STOCK).
                price(iPhone14Price).inventory(iPhone14Inventory).build();

        ResPriceDto resPriceDto = new ResPriceDto(iPhone14Price.getId(), iPhone14Price.getFinalPrice(),
                iPhone14Price.getDiscount(), iPhone14Price.getCurrency().getId(), iPhone14Price.getCurrency().getName());

        ResInventoryDto resInventoryDto = new ResInventoryDto(iPhone14Inventory.getId(), iPhone14Inventory.getQuantity(),
                iPhone14Inventory.getReservedQuantity());

        ResProductDto expectedResponse = new ResProductDto(1L, productName, productSlug, productDescription,
                productCategory.getId(), productCategory.getName(), ProductStatusType.OUT_OF_STOCK.name(),
                resPriceDto, resInventoryDto);

        when(productRepository.findById(1L)).thenReturn(Optional.of(iPhone14product));
        when(productMapper.toResProductDto(iPhone14product)).thenReturn(expectedResponse);

        ResProductDto result = productService.getProduct(1L);

        assertEquals(expectedResponse, result);

        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toResProductDto(iPhone14product);

    }

    @Test
    void save_whenReqProductDtoIsValid_shouldBeSaveProduct() {

        final ReqPriceDto reqPriceDto = new ReqPriceDto(BigDecimal.valueOf(1200), 0, 1L);
        final ReqInventoryDto reqInventoryDto = new ReqInventoryDto(500, 0);

        final String productName = "Samsung S24";
        final String productSlug = "Samsung S24 512G";
        final String productDescription = " Samsung cellPhone S24 512GB";

        final Category samsung24Category = createCellPhoneCategory();
        final Price samsung24Price = createPrice(1L, BigDecimal.valueOf(1200), 0);
        final Inventory samsung24Inventory = createInventory(1L, 500, 150);

        final ReqProductDto reqProductDto = new ReqProductDto(productName, productSlug, productDescription, 1L,
                ProductStatusType.ACTIVE.name(), reqPriceDto, reqInventoryDto);

        Product product = Product.builder().name(productName)
                .slug(productSlug).description(productDescription)
                .category(samsung24Category).statusType(ProductStatusType.ACTIVE).
                price(samsung24Price).inventory(samsung24Inventory).build();

        Product savedProduct = Product.builder().id(1L).name(productName)
                .slug(productSlug).description(productDescription)
                .category(samsung24Category).statusType(ProductStatusType.ACTIVE).
                price(samsung24Price).inventory(samsung24Inventory).build();

        ResPriceDto resPriceDto = new ResPriceDto(1L, samsung24Price.getFinalPrice(), samsung24Price.getDiscount(),
                samsung24Price.getCurrency().getId(), samsung24Price.getCurrency().getName());
        ResInventoryDto resInventoryDto = new ResInventoryDto(1L, 500, 0);

        ResProductDto resProductDto = new ResProductDto(1L, productName, productSlug, productDescription,
                samsung24Category.getId(), samsung24Category.getName(), ProductStatusType.ACTIVE.name(), resPriceDto,
                resInventoryDto);

        when(productMapper.toProduct(reqProductDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(productMapper.toResProductDto(product)).thenReturn(resProductDto);

        ResProductDto result = productService.save(reqProductDto);
        assertEquals(resProductDto, result);

        verify(productRepository, times(1)).save(product);
        verify(productMapper, times(1)).toProduct(reqProductDto);
        verify(productMapper, times(1)).toResProductDto(product);
    }

}