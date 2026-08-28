package com.naderaria.identity.web.controler;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commoncore.util.WebUtil;
import com.naderaria.identity.web.dto.authentication.request.ReqLoginDto;
import com.naderaria.identity.web.dto.authentication.respone.ResTokenDto;
import com.naderaria.identity.web.dto.user.request.ReqUserDto;
import com.naderaria.identity.web.dto.user.request.ReqUserUpdatableDto;
import com.naderaria.identity.web.dto.user.response.ResUpdatableUserDto;
import com.naderaria.identity.web.dto.user.response.ResUserDto;
import com.naderaria.identity.web.dto.user.response.ResUserPageItemDto;
import com.naderaria.identity.application.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/ecom/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<ResTokenDto> login(@RequestBody ReqLoginDto reqLoginDto) {
        return ResponseEntity
                .ok()
                .body(userService.login(reqLoginDto));

    }

    @PostMapping("/auth/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
    }


    @PostMapping("/auth/user")
    public ResponseEntity<ResUpdatableUserDto> register(@Validated @RequestBody ReqUserDto reqUserDto) {
        ResUpdatableUserDto resUpdatableUserDto = userService.register(reqUserDto);
        return ResponseEntity.created(WebUtil.createURI("/ecom/user/user/", resUpdatableUserDto.id()))
                .body(resUpdatableUserDto);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN') and hasPermission('User','read')")
    public ResponseEntity<PageResponse<ResUserPageItemDto>> getAllUserGroup(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean sortAscending) {

        PaginationDto reqBasePaginationDto = new PaginationDto(pageNumber, pageSize, sortAscending, new String[]{sortBy});

        return ResponseEntity.ok(userService.getUsers(reqBasePaginationDto));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('User') and hasPermission('User','read')")
    public ResponseEntity<ResUserDto> getProfile(@PathVariable("id") Long id) {
        ResUserDto resUserDto = userService.getProfile(id);
        return ResponseEntity.ok(resUserDto);
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('User') and hasPermission('User','update')")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Validated @RequestBody ReqUserUpdatableDto reqUserUpdatableDto) {
        userService.update(id, reqUserUpdatableDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN') and hasPermission('User','delete')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
