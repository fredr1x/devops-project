package pp.devopsfinal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp.devopsfinal.dto.LogActionDto;
import pp.devopsfinal.service.LogActionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
@Tag(name = "Logs", description = "Доступ к логам действий пользователей")
public class LogActionController {

    private final LogActionService logActionService;

    @Operation(summary = "Получить все логи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Логи успешно получены",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LogActionDto.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизован"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён")
    })
    @GetMapping("/all")
    public ResponseEntity<List<LogActionDto>> getAllLogs() {
        return ResponseEntity.ok(logActionService.getAll());
    }
}
