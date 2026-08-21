package com.naderaria.identity.domain;

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
@Table(name = "tb_location_info")
@SequenceGenerator(name = "sequence-generator", sequenceName = "loc_seq", allocationSize = 1)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class LocationInfo extends BaseEntity {

    @Column(name = "city")
    private String city;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "house_no")
    private String houseNo;
}
