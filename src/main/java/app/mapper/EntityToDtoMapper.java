package app.mapper;

import app.dto.UserDto;
import app.entity.User;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityToDtoMapper {
    public UserDto userToUserDto(User userDto);

    public User userDtoToUser(UserDto user);

    public List<UserDto> usersToUsersDto(List<User> userDto);

    default <T> T unwrap(Optional<T> optional) {
        return optional.orElse(null);
    }
}
