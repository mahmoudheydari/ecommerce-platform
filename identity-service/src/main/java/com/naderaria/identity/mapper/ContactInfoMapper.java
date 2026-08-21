package com.naderaria.identity.mapper;

import com.naderaria.identity.domain.ContactInfo;
import com.naderaria.identity.dto.contact_info.request.ReqContactInfoDto;
import com.naderaria.identity.dto.contact_info.response.ResContactInfoDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ContactInfoMapper {

    ContactInfo toContactInfo(ReqContactInfoDto reqContactInfoDto);

    ResContactInfoDto toResContactInfo(ContactInfo contactInfo);
}
