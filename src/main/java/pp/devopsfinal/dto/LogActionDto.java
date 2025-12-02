package pp.devopsfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogActionDto {
    private Long id;
    private Long userId;
    private String action;
    private Instant createdAt;
}
