package com.naderaria.commonsecurity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class CurrentUserResponse implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    private Collection<? extends GrantedAuthority> authorities;

    public CurrentUserResponse(Long id, String username, String password, boolean accountNonExpired,
                               boolean accountNonLocked, boolean credentialsNonExpired, boolean enable) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setAccountNonExpired(accountNonExpired);
        setAccountNonLocked(accountNonLocked);
        setCredentialsNonExpired(credentialsNonExpired);
        setEnabled(enable);

    }
}