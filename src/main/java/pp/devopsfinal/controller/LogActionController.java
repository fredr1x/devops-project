package pp.devopsfinal.controller;

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
public class LogActionController {

    private final LogActionService logActionService;

    @GetMapping("/all")
    public ResponseEntity<List<LogActionDto>> getAllLogs() {
        return ResponseEntity.ok().body(logActionService.getAll());
    }
}
