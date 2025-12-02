package pp.devopsfinal.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pp.devopsfinal.dto.LogActionDto;
import pp.devopsfinal.entity.LogAction;
import pp.devopsfinal.entity.User;
import pp.devopsfinal.enums.Action;
import pp.devopsfinal.mapper.LogActionMapper;
import pp.devopsfinal.repository.LogActionRepository;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogActionServiceTest {

    @Mock
    private LogActionRepository logActionRepository;

    @Mock
    private LogActionMapper logActionMapper;

    @InjectMocks
    private LogActionService logActionService;

    private User user;
    private LogAction logAction;
    private LogActionDto logActionDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        logAction = LogAction.builder()
                .user(user)
                .action(Action.LOGIN)
                .createdAt(Instant.now())
                .build();

        logActionDto = new LogActionDto();
        logActionDto.setUserId(1L);
        logActionDto.setAction("LOGIN");
    }

    @Test
    void log_ShouldSaveLogAction() {
        // when
        logActionService.log(user, Action.LOGIN);

        // then
        verify(logActionRepository).save(argThat(log ->
                log.getUser().equals(user) && log.getAction() == Action.LOGIN && log.getCreatedAt() != null
        ));
    }

    @Test
    void getAll_ShouldReturnListOfLogActionDto() {
        // given
        when(logActionRepository.findAll()).thenReturn(List.of(logAction));
        when(logActionMapper.toDto(logAction)).thenReturn(logActionDto);

        // when
        List<LogActionDto> result = logActionService.getAll();

        // then
        assertThat(result).containsExactly(logActionDto);
    }
}
