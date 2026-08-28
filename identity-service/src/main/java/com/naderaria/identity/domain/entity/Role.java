package com.naderaria.identity.domain.entity;

import com.naderaria.commondata.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "tb_role")
@SequenceGenerator(name = "sequence-generator", sequenceName = "role_seq", allocationSize = 1)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Role extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    public static final String ROLE_USER = "ROLE_User";
    public static final String ROLE_ADMIN = "ROLE_Admin";
}
