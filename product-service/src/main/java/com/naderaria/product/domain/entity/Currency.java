package com.naderaria.product.domain.entity;

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
@Table(name = "tb_currency")
@SequenceGenerator(name = "sequence-generator", sequenceName = "cur_seq", allocationSize = 1)
@Getter
@SuperBuilder
@NoArgsConstructor
public class Currency extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "symbol")
    private String symbol;

    @Setter
    @Column(name = "fraction_digits")
    private int fractionDigits;

}