package com.naderaria.identity.domain;


import com.naderaria.commondata.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_user")
@SequenceGenerator(name = "sequence-generator", sequenceName = "user_seq", allocationSize = 1)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "username", nullable = false, updatable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_location_info",referencedColumnName = "id")
    private LocationInfo locationInfo;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_contact_info", referencedColumnName = "id")
    private ContactInfo contactInfo;

    public User(Long id) {
        setId(id);
    }

}
