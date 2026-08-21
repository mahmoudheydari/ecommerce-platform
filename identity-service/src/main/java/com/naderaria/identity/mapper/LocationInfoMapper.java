package com.naderaria.identity.mapper;

import com.naderaria.identity.domain.LocationInfo;
import com.naderaria.identity.dto.location_info.request.ReqLocationInfoDto;
import com.naderaria.identity.dto.location_info.response.ResLocationInfoDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LocationInfoMapper {

    LocationInfo toLocationInfo(ReqLocationInfoDto reqLocationInfoDto);

    ResLocationInfoDto toResLocationInfo(LocationInfo locationInfo);

}
