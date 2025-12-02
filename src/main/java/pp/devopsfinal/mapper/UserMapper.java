package pp.devopsfinal.mapper;

import org.springframework.stereotype.Component;
import pp.devopsfinal.dto.UserDto;
import pp.devopsfinal.entity.User;

import java.util.List;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
            return UserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstname())
                    .lastName(user.getLastname())
                    .photoUrl(user.getPhotoUrl())
                    .role(user.getRole().name())
                    .build();
    }

    public List<UserDto> toDto(List<User> users) {
        return users.stream().map(this::toDto).toList();
    }
}
