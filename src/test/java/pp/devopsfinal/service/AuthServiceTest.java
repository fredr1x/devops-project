package pp.devopsfinal.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pp.devopsfinal.dto.LoginRequestDto;
import pp.devopsfinal.dto.RegisterRequestDto;
import pp.devopsfinal.dto.UserDto;
import pp.devopsfinal.entity.User;
import pp.devopsfinal.enums.Action;
import pp.devopsfinal.enums.Role;
import pp.devopsfinal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private LogActionService logActionService;

    @InjectMocks
    private AuthService authService;

    private RegisterRequestDto registerRequest;
    private LoginRequestDto loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequestDto();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");

        loginRequest = new LoginRequestDto();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encodedPassword")
                .firstname("John")
                .lastname("Doe")
                .role(Role.ROLE_USER)
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void register_ShouldThrow_WhenEmailAlreadyUsed() {
        // given
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        // when / then
        assertThrows(RuntimeException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any());
        verify(logActionService, never()).log(any(), any());
    }

    @Test
    void login_ShouldReturnUserDto_WhenCredentialsCorrect() {
        // given
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);

        // when
        UserDto result = authService.login(loginRequest);

        // then
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        verify(logActionService).log(user, Action.LOGIN);
    }

    @Test
    void login_ShouldThrow_WhenUserNotFound() {
        // given
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        // when / then
        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        verify(logActionService, never()).log(any(), any());
    }

    @Test
    void login_ShouldThrow_WhenPasswordInvalid() {
        // given
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

        // when / then
        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        verify(logActionService, never()).log(any(), any());
    }
}
