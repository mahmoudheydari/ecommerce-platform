package com.naderaria.product.domain.repository;

import com.naderaria.product.domain.entity.Product;
import com.naderaria.product.web.dto.intrernal.ProductSummeryDto;
import com.naderaria.product.web.dto.response.ResProductPageItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
                select new com.naderaria.product.web.dto.response.ResProductPageItemDto(p.id,p.name,p.description,
                            c.name,pr.amount,p.statusType,inv.quantity)
                from Product as p
                join p.category as c
                join p.price as pr
                join p.inventory as inv
            """,
            countQuery = """
                     select count(p) from Product p
                    """)
    Page<ResProductPageItemDto> findAllProducts(Pageable pageable);


    boolean existsByCategoryId(@PathVariable("categoryId") Long categoryId);

    @Query(value = """
        select p.price.amount from Product as p where p.id = :id
""")
    Optional<BigDecimal> getProductByProductId(@Param("id") Long id);

   @Query(value = """
                    select new com.naderaria.product.web.dto.intrernal.ProductSummeryDto(p.id,p.name,p.price.amount)
                    from Product as p
                    where p.id in(:productIds)
            """)
    Optional<List<ProductSummeryDto>> getProductsNameMap(@Param("productIds") List<Long> productIds);

}