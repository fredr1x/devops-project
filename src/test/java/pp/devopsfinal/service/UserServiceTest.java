package pp.devopsfinal.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pp.devopsfinal.dto.UserDto;
import pp.devopsfinal.entity.User;
import pp.devopsfinal.enums.Role;
import pp.devopsfinal.mapper.UserMapper;
import pp.devopsfinal.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setRole(Role.ROLE_USER);

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setRole("USER");
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // when
        User result = userService.getUser(1L);

        // then
        assertThat(result).isEqualTo(user);
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserNotFound() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // when / then
        assertThrows(RuntimeException.class, () -> userService.getUser(1L));
    }

    @Test
    void savePhotoUrl_ShouldUpdatePhotoUrl() {
        // given
        String photoUrl = "photo.jpg";

        // when
        userService.savePhotoUrl(user, photoUrl);

        // then
        assertThat(user.getPhotoUrl()).isEqualTo(photoUrl);
        verify(userRepository).save(user);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUserDtos() {
        // given
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        // when
        List<UserDto> result = userService.getAllUsers();

        // then
        assertThat(result).containsExactly(userDto);
    }

    @Test
    void getUserByIdDto_ShouldReturnUserDto() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        // when
        UserDto result = userService.getUserById(1L);

        // then
        assertThat(result).isEqualTo(userDto);
    }

    @Test
    void deleteUser_ShouldCallRepositoryDelete() {
        // when
        userService.deleteUser(1L);

        // then
        verify(userRepository).deleteById(1L);
    }
}
