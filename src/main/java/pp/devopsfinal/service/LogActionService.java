package pp.devopsfinal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pp.devopsfinal.entity.LogAction;
import pp.devopsfinal.entity.User;
import pp.devopsfinal.enums.Action;
import pp.devopsfinal.repository.LogActionRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LogActionService {

    private final LogActionRepository logActionRepository;

    public void log(User user, Action action) {
        LogAction log = LogAction.builder()
                .user(user)
                .action(action)
                .createdAt(Instant.now())
                .build();

        logActionRepository.save(log);
    }
}
