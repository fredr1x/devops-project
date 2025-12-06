package pp.devopsfinal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pp.devopsfinal.service.MinioService;

@RestController
@RequestMapping("/api/minio")
@RequiredArgsConstructor
@Tag(name = "MinIO", description = "Работа с профилями пользователей через MinIO")
public class MinioController {

    private final MinioService minioService;

    @Operation(summary = "Загрузить фото профиля пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл успешно загружен",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Ошибка загрузки файла"),
            @ApiResponse(responseCode = "401", description = "Не авторизован"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён")
    })
    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadProfilePhotoById(
            @Parameter(description = "ID пользователя", example = "1") @PathVariable Long id,
            @Parameter(description = "Файл для загрузки") @RequestParam("file") MultipartFile file
    ) throws Exception {
        String url = minioService.uploadProfilePhoto(
                id,
                file.getOriginalFilename(),
                file.getInputStream(),
                file.getContentType()
        );
        return ResponseEntity.ok(url);
    }

    @Operation(summary = "Получить файл фото профиля пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл успешно получен",
                    content = @Content(mediaType = "application/octet-stream",
                            schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "404", description = "Файл не найден"),
            @ApiResponse(responseCode = "401", description = "Не авторизован"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён")
    })
    @GetMapping("/photo-file/{id}")
    public ResponseEntity<InputStreamResource> getProfilePhotoFile(
            @Parameter(description = "ID пользователя", example = "1") @PathVariable Long id
    ) throws Exception {
        var inputStream = minioService.getProfilePhoto(id);
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok().body(resource);
    }
}
