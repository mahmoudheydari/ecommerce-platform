package com.naderaria.product.application.mapper;

import com.naderaria.product.domain.entity.Inventory;
import com.naderaria.product.web.dto.request.ReqInventoryDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface InventoryMapper {

    Inventory toInventory(ReqInventoryDto reqInventoryDto);

}