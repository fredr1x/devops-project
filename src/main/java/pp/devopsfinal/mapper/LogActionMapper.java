package pp.devopsfinal.mapper;

import org.springframework.stereotype.Component;
import pp.devopsfinal.dto.LogActionDto;
import pp.devopsfinal.entity.LogAction;

import java.util.List;

@Component
public class LogActionMapper {

    public LogActionDto toDto(LogAction logAction) {
        return LogActionDto.builder()
                .id(logAction.getId())
                .userId(logAction.getUser().getId())
                .action(logAction.getAction().name())
                .createdAt(logAction.getCreatedAt())
                .build();
    }

    public List<LogActionDto> toDto(List<LogAction> logActions) {
        return logActions.stream().map(this::toDto).toList();
    }
}
