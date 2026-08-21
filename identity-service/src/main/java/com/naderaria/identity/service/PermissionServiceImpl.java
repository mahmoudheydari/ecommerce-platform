package com.naderaria.identity.service;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.ApiResponse;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commoncore.exception.DataReferencedException;
import com.naderaria.commoncore.exception.DuplicateDataException;

import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.identity.domain.Permission;
import com.naderaria.identity.domain.Role;
import com.naderaria.identity.domain.RolePermission;
import com.naderaria.identity.dto.permission.request.ReqPermissionDto;
import com.naderaria.identity.dto.permission.request.ReqUpdatablePermissionDto;
import com.naderaria.identity.dto.permission.response.ResPermissionPageItemDto;
import com.naderaria.identity.dto.permission.response.ResUpdatablePermissionDto;
import com.naderaria.identity.dto.role.request.ReqRoleDto;
import com.naderaria.identity.dto.role.request.ReqUpdatableRoleDto;
import com.naderaria.identity.dto.role.response.ResRolePageItemDto;
import com.naderaria.identity.dto.role.response.ResUpdatableRoleDto;
import com.naderaria.identity.dto.role_permission.request.ReqRolePermissionDto;
import com.naderaria.identity.dto.role_permission.request.ReqUpdatableRolePermissionDto;
import com.naderaria.identity.dto.role_permission.response.ResRolePermissionDto;
import com.naderaria.identity.dto.role_permission.response.ResRolePermissionPageItemDto;
import com.naderaria.identity.mapper.PermissionMapper;
import com.naderaria.identity.mapper.RoleMapper;
import com.naderaria.identity.mapper.RolePermissionMapper;
import com.naderaria.identity.repository.PermissionRepository;
import com.naderaria.identity.repository.RolePermissionRepository;
import com.naderaria.identity.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor//todo refactor this class
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionMapper permissionMapper;
    private final RoleMapper roleMapper;
    private final RolePermissionMapper rolePermissionMapper;




    @Transactional
    @Override
    public PageResponse<ResPermissionPageItemDto> getAllPermissions(PaginationDto paginationDto, String targetType) {
        Pageable pageable = PageConvertor.convertToPageable(paginationDto);
        Page<Permission> permissions;
        if (targetType != null && !targetType.isBlank()) {
            permissions = permissionRepository.findAll(PermissionRepository.searchByTargetType(targetType), pageable);
        } else {
            permissions = permissionRepository.findAll(pageable);
        }
        return permissionMapper.toResPermissionPageItemDto(permissions);
    }

    @Transactional
    @Override
    public PageResponse<ResPermissionPageItemDto> getAllPermissionsByRoleId(PaginationDto paginationDto, long roleId) {//todo refactor this block;
        Pageable pageable = PageConvertor.convertToPageable(paginationDto);
        Page<Permission> permissions = rolePermissionRepository.findAllPermission(RolePermissionRepository.searchByRoleId(roleId), pageable);
        return permissionMapper.toResPermissionPageItemDto(permissions);
    }

    @Transactional
    @Override
    public ResUpdatablePermissionDto getPermission(long id) {
        Permission permission = permissionRepository.findById(id).orElseThrow(NullPointerException::new);
        return permissionMapper.toResUpdatablePermissionDto(permission);
    }

    @Transactional
    @Override
    public URI savePermission(ReqPermissionDto reqPermissionDto) {
        Permission permission = permissionMapper.toPermission(reqPermissionDto);
        validateDuplicatePermission(permission);
        permissionRepository.save(permission);
        try {
            return new URI(ServletUriComponentsBuilder.fromCurrentContextPath().path("/permission/permission/").toUriString() + permission.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    @Override
    public ResUpdatablePermissionDto updatePermission(ReqUpdatablePermissionDto reqUpdatablePermissionDto) {
        Permission permission = permissionMapper.toPermission(reqUpdatablePermissionDto);
        validateDuplicatePermission(permission);
        permissionRepository.save(permission);
        return permissionMapper.toResUpdatablePermissionDto(permission);
    }

    private void validateDuplicatePermission(Permission permission) {
        if (permissionRepository.exists(PermissionRepository
                .duplicatePermission(permission.getOperation(), permission.getTargetType())))
            throw new DuplicateDataException("Permission is already exists");
    }

    @Transactional
    @Override
    public ApiResponse<Void> deletePermission(long id) {
        checkRolePermissionDependOnIt(id);
        permissionRepository.deleteById(id);
        return ApiResponse.of("group.deleteAll");
    }

    private void checkRolePermissionDependOnIt(long permissionId) {
        if (rolePermissionRepository.findByPermissionId(permissionId) > 0)
            throw new DataReferencedException();
    }

    @Transactional
    @Override
    public ApiResponse<Void> deleteAllPermissionsByRoleId(long roleId) {
        rolePermissionRepository.deleteAllPermissionByRoleId(roleId);
        return ApiResponse.of("permission.deleteAllPermissionsByRoleId");
    }

    @Transactional
    @Override
    public ApiResponse<Void> deleteAllPermissions() {
        permissionRepository.deleteAll();
        return ApiResponse.of("permission.deleteAllPermissions");
    }

    @Transactional
    @Override
    public PageResponse<ResRolePageItemDto> getAllRoles(PaginationDto paginationDto, String title) {
        Pageable pageable = PageConvertor.convertToPageable(paginationDto);
        Page<Role> roles;
        if (title != null && title.isBlank()) {
            roles = roleRepository.findAll(RoleRepository.searchByTitle(title), pageable);
        } else {
            roles = roleRepository.findAll(pageable);
        }
        return roleMapper.toResRolePageItemDto(roles);
    }

    @Transactional
    @Override
    public ResUpdatableRoleDto getRole(long id) {
        Role role = roleRepository.findById(id).orElseThrow(NullPointerException::new);
        return roleMapper.toResUpdatableRoleDto(role);
    }

    @Transactional
    @Override
    public URI saveRole(ReqRoleDto reqRoleDto) {
        Role role = roleMapper.toRole(reqRoleDto);
        validateDuplicateRole(role);
        roleRepository.save(role);
        try {
            return new URI(ServletUriComponentsBuilder.fromCurrentContextPath().path("/lab/permission/role/").toUriString() + role.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    @Override
    public ResUpdatableRoleDto updateRole(ReqUpdatableRoleDto reqUpdatableRoleDto) {
        Role role = roleMapper.toRole(reqUpdatableRoleDto);
        validateDuplicateRole(role);
        roleRepository.save(role);
        return roleMapper.toResUpdatableRoleDto(role);
    }

    private void validateDuplicateRole(Role role) {
        if (roleRepository.exists(RoleRepository.duplicateRole(role.getTitle())))
            throw new DuplicateDataException("Role is already exists");
    }

    @Transactional
    @Override
    public ApiResponse<Void> deleteRole(long id) {
        rolePermissionRepository.deleteAllPermissionByRoleId(id);
        rolePermissionRepository.flush();
        roleRepository.deleteRoleById(id);
        return ApiResponse.of("role.deleteRole");
    }

    @Transactional
    @Override
    public ApiResponse<Void> deleteAllRoles() {
        rolePermissionRepository.deleteAll();
        rolePermissionRepository.flush();
        roleRepository.deleteAll();
        return ApiResponse.of("role.deleteAllRole");
    }

    @Transactional
    @Override
    public PageResponse<ResRolePermissionPageItemDto> getAllRolePermissions(PaginationDto paginationDto) {
        Pageable pageable = PageConvertor.convertToPageable(paginationDto);
        Page<RolePermission> rolePermissions = rolePermissionRepository.findAll(pageable);
        Map<Role, Permission> rolePermissionMap = new HashMap<>();//TODO can't put all rolePermissions into rolePermissionMap, role key is duplicate*/
        rolePermissions.stream().forEach(rolePermission -> rolePermissionMap.put(rolePermission.getRole(), rolePermission.getPermission()));
        return rolePermissionMapper.toResRolePermissionPageItemDto(rolePermissions);//TODO can't convert map to page
    }

    @Transactional
    @Override
    public URI saveRolePermission(ReqRolePermissionDto reqRolePermissionDto) {
        RolePermission rolePermission = rolePermissionMapper.toRolePermission(reqRolePermissionDto);
        validateDuplicateRolePermission(rolePermission);
        rolePermissionRepository.save(rolePermission);
        try {
            return new URI(ServletUriComponentsBuilder.fromCurrentContextPath().path("/permission/role/").toUriString() + rolePermission.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Transactional
    @Override
    public ResRolePermissionDto updateRolePermission(ReqUpdatableRolePermissionDto reqUpdatableRolePermissionDto) {
        RolePermission rolePermission = rolePermissionMapper.toRolePermission(reqUpdatableRolePermissionDto);
        validateDuplicateRolePermission(rolePermission);
        rolePermissionRepository.save(rolePermission);
        return rolePermissionMapper.toResRolePermissionDto(rolePermission);
    }


    private void validateDuplicateRolePermission(RolePermission rolePermission) {
        if (rolePermissionRepository
                .exists(RolePermissionRepository
                        .duplicateRolePermission(rolePermission.getRole().getId(), rolePermission.getPermission().getId())))
            throw new DuplicateDataException("RolePermission is already exists");
    }

    @Transactional
    @Override
    public ApiResponse<Void> deleteAllRolePermission(long id) {
        rolePermissionRepository.deleteById(id);
        return ApiResponse.of("RolePermissions.deleteAllRolePermissionsByRoleId");
    }

    @Transactional
    @Override
    public ApiResponse<Void> deleteAllRolePermissions() {
        rolePermissionRepository.deleteAll();
        return ApiResponse.of("RolePermissions.deleteAllRolePermissions");
    }
}
