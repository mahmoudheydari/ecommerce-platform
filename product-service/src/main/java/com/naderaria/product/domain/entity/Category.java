package com.naderaria.product.domain.entity;

import com.naderaria.commondata.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "tb_category")
@SequenceGenerator(name = "sequence-generator", sequenceName = "cat_seq", allocationSize = 1)

@SuperBuilder
@NoArgsConstructor
public class Category extends BaseEntity {

    @Getter
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_parent")
    private Category parent;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "active")
    private boolean active;

    @Getter
    @Setter
    @Column(name = "sort_order")
    private int sortOrder;// شماره مرتب سازی

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    public final void changeParent(Category parent) {
        if(this.parent != null){
            this.parent = parent;
        }
    }

}