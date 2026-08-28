package com.naderaria.identity.application.service;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.identity.web.dto.authentication.request.ReqLoginDto;
import com.naderaria.identity.web.dto.authentication.respone.ResTokenDto;
import com.naderaria.identity.web.dto.user.request.ReqUserDto;
import com.naderaria.identity.web.dto.user.request.ReqUserUpdatableDto;
import com.naderaria.identity.web.dto.user.response.ResUpdatableUserDto;
import com.naderaria.identity.web.dto.user.response.ResUserDto;
import com.naderaria.identity.web.dto.user.response.ResUserPageItemDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface UserService {

    ResTokenDto login(ReqLoginDto request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    PageResponse<ResUserPageItemDto> getUsers(PaginationDto paginationDto);

    ResUserDto getProfile(Long id);

    ResUpdatableUserDto register(ReqUserDto reqUserDto);

    void update(Long id, ReqUserUpdatableDto reqUserUpdatableDto);

    void delete(Long id);

}
