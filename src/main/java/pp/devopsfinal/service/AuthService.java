package pp.devopsfinal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pp.devopsfinal.dto.LoginRequestDto;
import pp.devopsfinal.dto.RegisterRequestDto;
import pp.devopsfinal.dto.UserDto;
import pp.devopsfinal.entity.User;
import pp.devopsfinal.enums.Action;
import pp.devopsfinal.enums.Role;
import pp.devopsfinal.repository.UserRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LogActionService logActionService;

    @Transactional
    public UserDto register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already used");
        }

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(Role.ROLE_USER)
                .createdAt(Instant.now())
                .build();

        var saved = userRepository.save(user);
        logActionService.log(user, Action.REGISTER);

        return toDto(saved);
    }

    public UserDto login(LoginRequestDto request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        logActionService.log(user, Action.LOGIN);
        return toDto(user);
    }

    private UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .photoUrl(user.getPhotoUrl())
                .role(user.getRole().name())
                .build();
    }
}
