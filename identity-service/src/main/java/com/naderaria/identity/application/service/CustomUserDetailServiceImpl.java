package com.naderaria.identity.application.service;

import com.naderaria.commonsecurity.dto.CurrentUserResponse;
import com.naderaria.identity.domain.repository.UserRepository;
import com.naderaria.identity.domain.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements CustomUserDetailService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetailsService userDetailService() {
        return username -> {
            CurrentUserResponse currentUserDto = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username + " not found "));

            List<? extends GrantedAuthority> roleAuthority =
                    userRoleRepository.getUserRolesByUserId(currentUserDto.getId()).orElseThrow(NullPointerException::new)
                            .stream().map(SimpleGrantedAuthority::new).toList();

            List<? extends GrantedAuthority> permissionAuthority =
                    userRoleRepository.getAllPermissionsByUserId(currentUserDto.getId())
                            .orElseThrow(NullPointerException::new).stream()
                            .map(rpd->new SimpleGrantedAuthority(rpd.operation()+"_"+rpd.targetType())).toList();

            currentUserDto.setAuthorities(new ArrayList<>(){{
                addAll(roleAuthority);
                addAll(permissionAuthority);
            }});
            return currentUserDto;
        };
    }
}
