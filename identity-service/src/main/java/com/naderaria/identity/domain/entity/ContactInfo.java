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
@Table(name = "tb_contact_info")
@SequenceGenerator(name = "sequence-generator", sequenceName = "cont_seq", allocationSize = 1)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ContactInfo extends BaseEntity {

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "cell_number")
    private String cellNumber;

    @Column(name = "email")
    private String email;

}
