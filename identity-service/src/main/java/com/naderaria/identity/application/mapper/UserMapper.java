package com.naderaria.identity.application.mapper;

import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.identity.domain.entity.User;
import com.naderaria.identity.web.dto.user.request.ReqUserDto;
import com.naderaria.identity.web.dto.user.request.ReqUserUpdatableDto;
import com.naderaria.identity.web.dto.user.response.ResUpdatableUserDto;
import com.naderaria.identity.web.dto.user.response.ResUserDto;
import com.naderaria.identity.web.dto.user.response.ResUserPageItemDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {LocationInfoMapper.class, ContactInfoMapper.class})
public interface UserMapper {


    @Mapping(target = "locationInfo" , source = "locationInfo")
    @Mapping(target = "contactInfo" , source = "contactInfo")
    User toUser(ReqUserDto reqUserDto);

    ResUserDto toResUserDto(User user);

    default PageResponse<ResUserPageItemDto> toResUserPageItemDto(Page<User> userGroups) {
        return PageConvertor.toPageableDto(userGroups, this::toResUserPageItemDto);
    }

    @Mapping(target = "city", source = "locationInfo.city")
    @Mapping(target = "phoneNumber", source = "contactInfo.phoneNumber")
    ResUserPageItemDto toResUserPageItemDto(User user);

    User toUser(ReqUserUpdatableDto reqUserUpdatableDto);

    void update(ReqUserUpdatableDto reqUpdatableProductDto, @MappingTarget User user);

    ResUpdatableUserDto toResUpdatableUserDto(User user);
}