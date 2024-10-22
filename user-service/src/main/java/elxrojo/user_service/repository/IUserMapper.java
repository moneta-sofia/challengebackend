package elxrojo.user_service.repository;

import elxrojo.user_service.model.DTO.UserDTO;
import elxrojo.user_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IUserMapper {
    User updateUser(UserDTO userDTO, @MappingTarget User user);
}
