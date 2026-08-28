package com.naderaria.identity.application.mapper;

import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.identity.domain.entity.Permission;
import com.naderaria.identity.web.dto.permission.request.ReqPermissionDto;
import com.naderaria.identity.web.dto.permission.request.ReqUpdatablePermissionDto;
import com.naderaria.identity.web.dto.permission.response.ResPermissionPageItemDto;
import com.naderaria.identity.web.dto.permission.response.ResUpdatablePermissionDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PermissionMapper {

    default PageResponse<ResPermissionPageItemDto> toResPermissionPageItemDto(Page<Permission> permissions){
        return PageConvertor.toPageableDto(permissions, this::toResPermissionPageItemDto);
    }

    ResPermissionPageItemDto toResPermissionPageItemDto(Permission permission);

    ResUpdatablePermissionDto toResUpdatablePermissionDto(Permission permission);

    Permission toPermission(ReqPermissionDto reqPermissionDto);

    Permission toPermission(ReqUpdatablePermissionDto reqUpdatablePermissionDto);
}
