package com.naderaria.identity.repository;

import com.naderaria.identity.domain.Permission;
import com.naderaria.identity.domain.RolePermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long>, JpaSpecificationExecutor<RolePermission> {


    @Query(value = """
                select p
                from RolePermission rp
                join Permission as p on p.id = rp.permission.id
            """)
    Page<Permission> findAllPermission(Specification<RolePermission> rolePermissionSpecification, Pageable pageable);


    void deleteAllPermissionByRoleId(@Param("roleId") long roleId);

    @Query(value = """
                    select count(rp.id) from RolePermission as rp where rp.permission.id = :permissionId
            """)
    int findByPermissionId(long permissionId);//todo change method name


    static Specification<RolePermission> searchByRoleId(Long roleId) {
        return (root, query, cb) ->
                cb.equal(root.get("role").get("id"), roleId);
    }

    static Specification<RolePermission> duplicateRolePermission(Long roleId, Long permissionId) {
        return (root, query, cb) ->
                cb.and(
                        cb.equal(root.get("role").get("id"), roleId),
                        cb.equal(root.get("permission").get("id"), permissionId)
                );
    }
}
