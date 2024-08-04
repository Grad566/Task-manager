package hexlet.code.service;

import hexlet.code.dto.userDTO.UserCreatedDTO;
import hexlet.code.dto.userDTO.UserDTO;
import hexlet.code.dto.userDTO.UserUpdatedDTO;
import hexlet.code.mapper.UserMapper;
import hexlet.code.repository.UserRepository;
import hexlet.code.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserUtil userUtil;

    public UserDTO show(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow();
        return userMapper.map(user);
    }

    public List<UserDTO> getAll() {
        var users = userRepository.findAll();
        return users.stream().map(userMapper::map).toList();
    }

    public UserDTO create(UserCreatedDTO data) {
        var user = userMapper.map(data);
        userRepository.save(user);

        return userMapper.map(user);
    }

    @SneakyThrows
    public UserDTO update(UserUpdatedDTO data, Long id) {
        if (userUtil.isTheSameUser(id)) {
            var user = userRepository.findById(id)
                    .orElseThrow();
            userMapper.update(data, user);

            userRepository.save(user);

            return userMapper.map(user);
        } else {
            throw new AccessException("One user can't change another user");
        }
    }

    @SneakyThrows
    public void destroy(Long id) {
        if (userUtil.isTheSameUser(id)) {
            userRepository.deleteById(id);
        } else {
            throw new AccessException("One user can't change another user");
        }
    }
}
