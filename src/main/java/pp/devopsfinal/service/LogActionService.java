package pp.devopsfinal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pp.devopsfinal.dto.LogActionDto;
import pp.devopsfinal.entity.LogAction;
import pp.devopsfinal.entity.User;
import pp.devopsfinal.enums.Action;
import pp.devopsfinal.mapper.LogActionMapper;
import pp.devopsfinal.repository.LogActionRepository;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LogActionService {

    private final LogActionRepository logActionRepository;
    private final LogActionMapper logActionMapper;

    public void log(User user, Action action) {
        LogAction log = LogAction.builder()
                .user(user)
                .action(action)
                .createdAt(Instant.now())
                .build();

        logActionRepository.save(log);
    }

    public List<LogActionDto> getAll() {
        return logActionRepository.findAll().stream().map(logActionMapper::toDto).toList();
    }
}
