package pp.devopsfinal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pp.devopsfinal.dto.UserDto;
import pp.devopsfinal.entity.User;
import pp.devopsfinal.enums.Role;
import pp.devopsfinal.mapper.UserMapper;
import pp.devopsfinal.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void savePhotoUrl(User user, String objectName) {
        user.setPhotoUrl(objectName);
        userRepository.save(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getUserById(Long id) {
        return userMapper.toDto(getUser(id));
    }

    @Transactional
    public UserDto updateUserRole(Long id, String role) {
        User user = getUser(id);
        user.setRole(Role.valueOf(role));
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
