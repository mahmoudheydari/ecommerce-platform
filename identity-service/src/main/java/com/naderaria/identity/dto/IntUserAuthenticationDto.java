package com.naderaria.identity.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntUserAuthenticationDto implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    private List<String> roleNames;


    public IntUserAuthenticationDto(Long id, String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setAccountNonExpired(accountNonExpired);
        setAccountNonLocked(accountNonLocked);
        setCredentialsNonExpired(credentialsNonExpired);
        setEnabled(enabled);
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleNames.stream().map(SimpleGrantedAuthority::new).toList();
    }
}
