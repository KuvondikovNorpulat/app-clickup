package uz.kuvondikov.clickup.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uz.kuvondikov.clickup.dto.user.AuthUserDto;
import uz.kuvondikov.clickup.dto.user.AuthUserRegisterDto;
import uz.kuvondikov.clickup.dto.user.AuthUserUpdateDto;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.mapper.base.BaseMapper;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthUserMapper extends BaseMapper {
    List<AuthUserDto> toDTO(List<AuthUser> authUsers);

    AuthUserDto toDTO(AuthUser authUser);

    AuthUser fromRegisterDTO(AuthUserRegisterDto userRegisterDto);

    AuthUser fromUpdateDTO(AuthUserUpdateDto updateDTO, @MappingTarget AuthUser target);
}