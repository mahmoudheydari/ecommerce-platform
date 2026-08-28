package com.naderaria.identity.application.service;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.ApiResponse;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.identity.web.dto.permission.request.ReqPermissionDto;
import com.naderaria.identity.web.dto.permission.request.ReqUpdatablePermissionDto;
import com.naderaria.identity.web.dto.permission.response.ResPermissionPageItemDto;
import com.naderaria.identity.web.dto.permission.response.ResUpdatablePermissionDto;
import com.naderaria.identity.web.dto.role.request.ReqRoleDto;
import com.naderaria.identity.web.dto.role.request.ReqUpdatableRoleDto;
import com.naderaria.identity.web.dto.role.response.ResRolePageItemDto;
import com.naderaria.identity.web.dto.role.response.ResUpdatableRoleDto;
import com.naderaria.identity.web.dto.role_permission.request.ReqRolePermissionDto;
import com.naderaria.identity.web.dto.role_permission.request.ReqUpdatableRolePermissionDto;
import com.naderaria.identity.web.dto.role_permission.response.ResRolePermissionDto;
import com.naderaria.identity.web.dto.role_permission.response.ResRolePermissionPageItemDto;


import java.net.URI;

public interface PermissionService {

    PageResponse<ResPermissionPageItemDto> getAllPermissions(PaginationDto paginationDto, String targetType);

    PageResponse<ResPermissionPageItemDto> getAllPermissionsByRoleId(PaginationDto paginationDto, long roleId);

    ResUpdatablePermissionDto getPermission(long id);

    URI savePermission(ReqPermissionDto reqPermissionDto);

    ResUpdatablePermissionDto updatePermission(ReqUpdatablePermissionDto reqUpdatablePermissionDto);

    ApiResponse<Void> deletePermission(long id);

    ApiResponse<Void> deleteAllPermissionsByRoleId(long roleId);

    ApiResponse<Void> deleteAllPermissions();

    PageResponse<ResRolePageItemDto> getAllRoles(PaginationDto paginationDto, String title);

    ResUpdatableRoleDto getRole(long id);

    URI saveRole(ReqRoleDto reqRoleDto);

    ResUpdatableRoleDto updateRole(ReqUpdatableRoleDto reqUpdatableRoleDto);

    ApiResponse<Void> deleteRole(long id);

    ApiResponse<Void> deleteAllRoles();

    PageResponse<ResRolePermissionPageItemDto> getAllRolePermissions(PaginationDto paginationDto);

    URI saveRolePermission(ReqRolePermissionDto reqRolePermissionDto);

    ResRolePermissionDto updateRolePermission(ReqUpdatableRolePermissionDto reqUpdatableRolePermissionDto);

    ApiResponse<Void> deleteAllRolePermission(long id);

    ApiResponse<Void> deleteAllRolePermissions();

}
