package com.naderaria.identity.domain.entity;


import com.naderaria.commondata.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_permission")
@SequenceGenerator(name = "sequence-generator", sequenceName = "prm_seq", allocationSize = 1)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Permission extends BaseEntity {

    @Column(name = "operation", nullable = false)
    private String operation; // READ,WRITE

    @Column(name = "target_type", nullable = false)
    private String targetType; // USER,ManagementType,

    @Column(name = "title", nullable = false)
    private String title; // نمایش برای UI یا مدیر سیستم

}
