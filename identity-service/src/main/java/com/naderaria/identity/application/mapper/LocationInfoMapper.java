package com.naderaria.identity.application.mapper;

import com.naderaria.identity.domain.entity.LocationInfo;
import com.naderaria.identity.web.dto.location_info.request.ReqLocationInfoDto;
import com.naderaria.identity.web.dto.location_info.response.ResLocationInfoDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LocationInfoMapper {

    LocationInfo toLocationInfo(ReqLocationInfoDto reqLocationInfoDto);

    ResLocationInfoDto toResLocationInfo(LocationInfo locationInfo);

}
