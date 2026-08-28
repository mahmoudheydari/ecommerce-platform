package com.naderaria.identity.application.service;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.commonsecurity.config.JwtProperties;
import com.naderaria.commonsecurity.dto.CurrentUserResponse;
import com.naderaria.commonsecurity.service.JwtService;
import com.naderaria.commonsecurity.service.JwtServiceImpl;
import com.naderaria.identity.domain.entity.*;
import com.naderaria.identity.web.dto.authentication.request.ReqLoginDto;
import com.naderaria.identity.web.dto.authentication.respone.ResTokenDto;
import com.naderaria.identity.web.dto.contact_info.request.ReqContactInfoDto;
import com.naderaria.identity.web.dto.location_info.request.ReqLocationInfoDto;
import com.naderaria.identity.web.dto.user.request.ReqUserDto;
import com.naderaria.identity.web.dto.user.response.ResUpdatableUserDto;
import com.naderaria.identity.web.dto.user.response.ResUserPageItemDto;
import com.naderaria.identity.application.mapper.UserMapper;
import com.naderaria.identity.domain.repository.RoleRepository;
import com.naderaria.identity.domain.repository.UserRepository;
import com.naderaria.identity.domain.repository.UserRoleRepository;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void login_shouldReturnTokens_whenCredentialsAreValid() {
        //Arrange
        ReqLoginDto adminLoginDto = new ReqLoginDto("admin", "modnit");
        CurrentUserResponse currentUserResponse =
                new CurrentUserResponse(1L, "admin", "modnit", true,
                        true, true, true);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(currentUserResponse);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(jwtService.generateToken(currentUserResponse)).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(currentUserResponse)).thenReturn("refreshToken");

        //Act
        ResTokenDto result = userService.login(adminLoginDto);

        //Assert
        assertNotNull(result);
        assertEquals("accessToken", result.accessToken());
        assertEquals("refreshToken", result.refreshToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(currentUserResponse);
        verify(jwtService).generateRefreshToken(currentUserResponse);

    }

    @Test
    void login_shouldGenerateValidJwtToken() {

        //Arrange
        ReqLoginDto adminLoginDto = new ReqLoginDto("admin", "modnit");

        CurrentUserResponse currentUserResponse = new CurrentUserResponse(1L, "admin", "modnit", true,
                true, true, true);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_Admin"));
        grantedAuthorities.add(new SimpleGrantedAuthority("write_Permission"));
        grantedAuthorities.add(new SimpleGrantedAuthority("read_Permission"));
        grantedAuthorities.add(new SimpleGrantedAuthority("update_Permission"));
        grantedAuthorities.add(new SimpleGrantedAuthority("delete_Permission"));

        currentUserResponse.setAuthorities(grantedAuthorities);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(currentUserResponse);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);


        JwtProperties realProps = new JwtProperties(
                "AbCdEfGhIjKlMnOpQrStUvWxYz1234567890+AbCdEfGhIjKlmNoPqRsTu",  // signingKey
                "AbCdEfGhIjKlMnOpQrStUvWxYz1234567890+AbCdEfGhIjKlmNoPqRsTu",  // refreshSigningKey
                900000L,
                604800000L
        );
        JwtServiceImpl realJwtService = new JwtServiceImpl(realProps);
        String realAccessToken = realJwtService.generateToken(currentUserResponse);

        when(jwtService.generateToken(currentUserResponse)).thenReturn(realAccessToken);
        when(jwtService.generateRefreshToken(currentUserResponse)).thenReturn("fake-refresh-token");

        //Act
        ResTokenDto resTokenDto = userService.login(adminLoginDto);

        //Assert
        assertNotNull(resTokenDto);
        assertNotNull(resTokenDto.accessToken());

        try {
            Claims claims = realJwtService.parseToken(resTokenDto.accessToken());
            assertEquals(claims.get("id", Long.class), currentUserResponse.getId());
            assertEquals(claims.getSubject(), currentUserResponse.getUsername());

            @SuppressWarnings("unchecked")
            List<String> authorities = claims.get("authorities", List.class);
            assertNotNull(authorities);
            assertFalse(authorities.isEmpty());
            assertTrue(authorities.contains("ROLE_Admin") ||
                    authorities.contains("write_Permission") ||
                    authorities.contains("read_Permission") ||
                    authorities.contains("update_Permission") ||
                    authorities.contains("delete_Permission")
            );

        } catch (Exception e) {
            fail("JWT token invalid: " + e.getMessage());
        }
    }

    @Test
    void getUsers_shouldReturnUsers_whenPaginationAreValid() {
        //Arrange
        PaginationDto paginationDto = new PaginationDto(1, 10, true, new String[]{"id"});
        Pageable pageable = PageConvertor.convertToPageable(paginationDto);

        List<User> users = List.of(
                User.builder()
                        .id(1L)
                        .username("admin")
                        .password("modnit")
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .locationInfo(LocationInfo.builder().city("tehran").build())
                        .contactInfo(ContactInfo.builder().phoneNumber("09359974976").build())
                        .build(),
                User.builder().id(2L)
                        .username("nader")
                        .password("modnit")
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .locationInfo(LocationInfo.builder().city("tehran").build())
                        .contactInfo(ContactInfo.builder().phoneNumber("09359974976").build())
                        .build(),
                User.builder().id(3L)
                        .username("arezo")
                        .password("modnit")
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .locationInfo(LocationInfo.builder().city("tehran").build())
                        .contactInfo(ContactInfo.builder().phoneNumber("09359974976").build())
                        .build());
        Page<User> userPage = new PageImpl<>(users, pageable, users.size());

        List<ResUserPageItemDto> resUserPageItemDtoList = users.stream().map(u -> new ResUserPageItemDto(u.getId(),
                u.getUsername(), u.isAccountNonExpired(), u.isAccountNonLocked(), u.isCredentialsNonExpired(),
                u.getLocationInfo().getCity(), u.getContactInfo().getPhoneNumber(), u.isEnabled())).toList();

        PageResponse<ResUserPageItemDto> expectedResponse = new PageResponse<>(resUserPageItemDtoList, 0, 1,
                (long) resUserPageItemDtoList.size());

        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.toResUserPageItemDto(userPage)).thenReturn(expectedResponse);

        //Act
        PageResponse<ResUserPageItemDto> resultPage = userService.getUsers(paginationDto);

        //Assert
        assertEquals(resultPage.getTotalElements(), userPage.getTotalElements());
        assertEquals(resultPage.getTotalPages(), userPage.getTotalPages());
        assertEquals(resultPage.getPage(), userPage.getNumber());

        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    void register_shouldSaveUserAndAssignDefaultRole() {

        //Arrange
        ReqUserDto reqUserDto = new ReqUserDto(
                "nader",
                "password123",
                new ReqLocationInfoDto("Tehran", "Valiasr St", "12"),
                new ReqContactInfoDto("02112345678", "09121234567", "nader@gmail.com")
        );

        User mappedUser = new User();
        mappedUser.setUsername("nader");


        Role userRole = new Role();
        userRole.setTitle(Role.ROLE_USER);

        ResUpdatableUserDto expectedResponse = new ResUpdatableUserDto(1L, "nader", true,
                true, true, true, null, null);

        when(userMapper.toUser(reqUserDto)).thenReturn(mappedUser);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });
        when(roleRepository.findByTitle(Role.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(userMapper.toResUpdatableUserDto(any(User.class))).thenReturn(expectedResponse);



        ResUpdatableUserDto result = userService.register(reqUserDto);

        assertThat(result).isEqualTo(expectedResponse);

        verify(passwordEncoder).encode("password123");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertThat(savedUser.getUsername()).isEqualTo("nader");
        assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
        assertThat(savedUser.isAccountNonExpired()).isTrue();
        assertThat(savedUser.isAccountNonLocked()).isTrue();
        assertThat(savedUser.isCredentialsNonExpired()).isTrue();
        assertThat(savedUser.isEnabled()).isTrue();

        ArgumentCaptor<UserRole> userRoleCaptor = ArgumentCaptor.forClass(UserRole.class);
        verify(userRoleRepository).save(userRoleCaptor.capture());
        UserRole savedUserRole = userRoleCaptor.getValue();

        assertThat(savedUserRole.getRole()).isEqualTo(userRole);
        assertThat(savedUserRole.getUser()).isEqualTo(savedUser);

        verify(roleRepository).findByTitle(Role.ROLE_USER);
        verify(userMapper).toResUpdatableUserDto(savedUser);

    }

    @Test
    @DisplayName("should throw NullPointerException when default ROLE_User not found")
    void register_shouldThrowWhenDefaultRoleNotFound() {
        //Arrange
        ReqUserDto reqUserDto = new ReqUserDto(
                "nader",
                "password123",
                new ReqLocationInfoDto("Tehran", "Valiasr St", "12"),
                new ReqContactInfoDto("02112345678", "09121234567", "nader@gmail.com")
        );

        User mappedUser = new User();
        mappedUser.setUsername("mahmoud");

        when(userMapper.toUser(reqUserDto)).thenReturn(mappedUser);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(mappedUser);
        when(roleRepository.findByTitle(Role.ROLE_USER)).thenReturn(Optional.empty());

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(
                NullPointerException.class,
                () -> userService.register(reqUserDto)
        );

        verify(userRoleRepository, never()).save(any());
    }

}