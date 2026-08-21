package com.naderaria.identity.domain;


import com.naderaria.commondata.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_role_permission")
@SequenceGenerator(name = "sequence-generator",sequenceName = "role_per_seq",allocationSize = 1)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class RolePermission extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fk_role", nullable = false, referencedColumnName = "id")
    private Role role;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fk_permission", nullable = false, referencedColumnName = "id")
    private Permission permission;

}
