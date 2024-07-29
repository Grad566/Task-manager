package hexlet.code.service;

import hexlet.code.dto.UserCreatedDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.dto.UserUpdatedDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO show(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with " + id + " not found!"));
        return userMapper.map(user);
    }

    public List<UserDTO> getAll() {
        var users = userRepository.findAll();
        return users.stream().map(userMapper::map).toList();
    }

    @Transactional
    public UserDTO create(UserCreatedDTO data) {
        var user = userMapper.map(data);
        userRepository.save(user);

        return userMapper.map(user);
    }

    @Transactional
    public UserDTO update(UserUpdatedDTO data, Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with " + id + " not found!"));
        userMapper.update(data, user);

        userRepository.save(user);

        return userMapper.map(user);
    }

    public void destroy(Long id) {
        userRepository.deleteById(id);
    }
}
