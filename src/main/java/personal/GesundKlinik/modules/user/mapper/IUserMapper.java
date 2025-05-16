package personal.GesundKlinik.modules.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import personal.GesundKlinik.modules.user.dto.UserRegisterRequest;
import personal.GesundKlinik.modules.user.dto.UserRegisterResponse;
import personal.GesundKlinik.modules.user.entity.User;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(final UserRegisterRequest request);

    UserRegisterResponse toSaveResponse(final User entity);

}
