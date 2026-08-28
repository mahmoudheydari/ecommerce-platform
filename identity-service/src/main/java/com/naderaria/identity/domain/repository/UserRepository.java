package com.naderaria.identity.domain.repository;

import com.naderaria.commonsecurity.dto.CurrentUserResponse;
import com.naderaria.identity.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
                        select
                            new com.naderaria.commonsecurity.dto.CurrentUserResponse
                                        (u.id,u.username,u.password,u.accountNonExpired,u.accountNonLocked,
                                                    u.credentialsNonExpired,u.enabled)
                        from User as u
                        where u.username = :username
            """)
    Optional<CurrentUserResponse> findByUsername(@Param("username") String username);
}
