package pp.devopsfinal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pp.devopsfinal.entity.User;
import pp.devopsfinal.repository.UserRepository;
import pp.devopsfinal.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstname("John")
                .lastname("Doe")
                .password("hashedpassword")
                .build();
    }

    @Test
    void testGetUserSuccess() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.getUser("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testGetUserNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUser("unknown@example.com");
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByEmail("unknown@example.com");
    }

    @Test
    void testSavePhotoUrl() {
        String photoUrl = "1/profile.jpg";
        userService.savePhotoUrl(user, photoUrl);

        assertEquals(photoUrl, user.getPhotoUrl());
    }

}
