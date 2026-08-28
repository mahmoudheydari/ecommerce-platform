package com.naderaria.identity.application.mapper;


import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.identity.domain.entity.Role;
import com.naderaria.identity.web.dto.role.request.ReqRoleDto;
import com.naderaria.identity.web.dto.role.request.ReqUpdatableRoleDto;
import com.naderaria.identity.web.dto.role.response.ResRolePageItemDto;
import com.naderaria.identity.web.dto.role.response.ResUpdatableRoleDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = PermissionMapper.class)
public interface RoleMapper {

    default PageResponse<ResRolePageItemDto> toResRolePageItemDto(Page<Role> roles) {
        return PageConvertor.toPageableDto(roles, this::toResRolePageItemDto);
    }

    ResRolePageItemDto toResRolePageItemDto(Role role);

    ResUpdatableRoleDto toResUpdatableRoleDto(Role role);

    Role toRole(ReqRoleDto reqRoleDto);


    Role toRole(ReqUpdatableRoleDto reqUpdatableRoleDto);

}
