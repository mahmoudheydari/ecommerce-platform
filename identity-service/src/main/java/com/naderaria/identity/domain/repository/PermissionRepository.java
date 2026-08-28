package com.naderaria.identity.domain.repository;

import com.naderaria.identity.domain.entity.Permission;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {


    //Specifications
    static Specification<Permission> searchByTargetType(String targetType) {
        return (root, query, cb) -> {
            return cb.like(cb.lower(root.get("targetType")), "%" + targetType.toLowerCase() + "%");
        };
    }

    static Specification<Permission> duplicatePermission(String operation, String targetType) {
        return (root, query, cb) ->
                cb.and(
                        cb.equal(root.get("operation"), operation),
                        cb.equal(root.get("targetType"), targetType)
                );
    }

}
