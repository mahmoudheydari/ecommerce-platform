package com.naderaria.identity.service;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.commonsecurity.dto.CurrentUserResponse;
import com.naderaria.commonsecurity.dto.JwtTokenResponse;
import com.naderaria.commonsecurity.service.JwtService;
import com.naderaria.identity.domain.Role;
import com.naderaria.identity.domain.User;
import com.naderaria.identity.domain.UserRole;
import com.naderaria.identity.dto.authentication.request.ReqLoginDto;
import com.naderaria.identity.dto.authentication.respone.ResTokenDto;
import com.naderaria.identity.dto.user.request.ReqUserDto;
import com.naderaria.identity.dto.user.request.ReqUserUpdatableDto;
import com.naderaria.identity.dto.user.response.ResUpdatableUserDto;
import com.naderaria.identity.dto.user.response.ResUserDto;
import com.naderaria.identity.dto.user.response.ResUserPageItemDto;
import com.naderaria.identity.mapper.UserMapper;
import com.naderaria.identity.repository.RoleRepository;
import com.naderaria.identity.repository.UserRepository;
import com.naderaria.identity.repository.UserRoleRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public ResTokenDto login(ReqLoginDto reqLoginDto) {

        final String username = reqLoginDto.username();
        final String password = reqLoginDto.password();

        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        CurrentUserResponse currentUserDto = (CurrentUserResponse) authenticate.getPrincipal();
        String accessToken = jwtService.generateToken(currentUserDto);
        String refreshToken = jwtService.generateRefreshToken(currentUserDto);
        return new ResTokenDto(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.isNotBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String refreshToken = authHeader.substring((7));
        String username = jwtService.extractUsername(refreshToken);

        if (StringUtils.isNotBlank(username)) {

            final CurrentUserResponse currentUserDto = getUserAuthentication(username);

            if (jwtService.isTokenValid(refreshToken, currentUserDto.getUsername())) {

                String accessToken = jwtService.generateToken(currentUserDto);
                refreshToken = jwtService.generateRefreshToken(currentUserDto);

                ResTokenDto resTokenDto = new ResTokenDto(accessToken, refreshToken);

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpStatus.ACCEPTED.value());
                OutputStream responseStream = response.getOutputStream();
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(responseStream, resTokenDto);
                responseStream.flush();

            }
        }
    }

    private CurrentUserResponse getUserAuthentication(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> UsernameNotFoundException.fromUsername(username));
    }

    @Override
    @Transactional
    public PageResponse<ResUserPageItemDto> getUsers(PaginationDto reqBasePaginationDto) {
        Pageable pageable = PageConvertor.convertToPageable(reqBasePaginationDto);
        Page<User> users = userRepository.findAll(pageable);
        return userMapper.toResUserPageItemDto(users);
    }

    @Override
    @Transactional
    public ResUserDto getProfile(Long id) {
        JwtTokenResponse jwtTokenDto = (JwtTokenResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id != null && jwtTokenDto != null) {
            if (jwtTokenDto.id().equals(id)) {
                User user = userRepository.findById(id).orElseThrow(NullPointerException::new);
                return userMapper.toResUserDto(user);
            }
        }
        throw new UsernameNotFoundException("User not found");

    }

    @Override
    @Transactional
    public ResUpdatableUserDto register(ReqUserDto reqUserDto) {
        User user = userMapper.toUser(reqUserDto);
        fillDefaultData(user);
        user.setPassword(passwordEncoder.encode(reqUserDto.password()));
        userRepository.save(user);
        updateUserDefaultRole(user);
        return userMapper.toResUpdatableUserDto(user);
    }

    private void fillDefaultData(User user) {
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

    }

    private void updateUserDefaultRole(User user) {
        UserRole userRole = new UserRole();
        Role role = roleRepository.findByTitle(Role.ROLE_USER).orElseThrow(NullPointerException::new);
        userRole.setRole(role);
        userRole.setUser(user);
        userRoleRepository.save(userRole);
    }


    @Override
    @Transactional
    public void update(Long id, ReqUserUpdatableDto reqUserUpdatableDto) {
        User oldUser = userRepository.findById(id).orElseThrow(NullPointerException::new);
        userMapper.update(reqUserUpdatableDto, oldUser);
        userRepository.save(oldUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
