package com.naderaria.product.domain.repository;

import com.naderaria.product.domain.entity.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface PriceRepository extends CrudRepository<Price, Long> {

    boolean existsByCurrencyId(@PathVariable("currencyId") Long currencyId);

}