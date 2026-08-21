package com.naderaria.identity.repository;

import com.naderaria.identity.domain.Role;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    @Modifying
    @Query(value = """
                    delete Role as r where r.id = :id
            """)
    void deleteRoleById(@Param("id") long id);

    @Modifying
    @Query(value = """
                    delete Role as r
            """)
    void deleteAllRole();

    //Specifications
    static Specification<Role> searchByTitle(@NonNull String title) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");

    }

    static Specification<Role> duplicateRole(String title) {
        return (root, query, cb) ->
                cb.equal(root.get("title"), title);

    }

    Optional<Role> findByTitle(String roleTitle);


}