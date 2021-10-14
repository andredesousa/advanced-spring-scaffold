package app.service;

import app.dto.UserDto;
import app.entity.User;
import app.mapper.EntityToDtoMapper;
import app.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private transient UserRepository userRepository;

    @Autowired
    private transient EntityToDtoMapper mapper;

    @Autowired
    private transient BCryptPasswordEncoder passwordEncoder;

    /**
     * Gets a list of users.
     * @return A list of users.
     */
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();

        return mapper.usersToUsersDto(users);
    }

    /**
     * Gets a user by id.
     * @param id - The id of the user.
     * @return The user.
     */
    public UserDto findById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        return mapper.userToUserDto(mapper.unwrap(user));
    }

    /**
     * Creates a user.
     * @param user - The user to insert.
     * @return The inserted user.
     */
    public UserDto create(UserDto user) {
        User newUser = mapper.userDtoToUser(user);
        newUser.setPassword(passwordEncoder.encode(user.password));

        return mapper.userToUserDto(userRepository.save(newUser));
    }

    /**
     * Updates a user.
     * @param user - The user to update.
     * @return The updated user.
     */
    public UserDto update(UserDto user) {
        User updatedUser = mapper.userDtoToUser(user);
        updatedUser.setPassword(passwordEncoder.encode(user.password));

        return mapper.userToUserDto(userRepository.save(updatedUser));
    }

    /**
     * Deletes a user by id.
     * @param id - The id of the user to delete.
     */
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
