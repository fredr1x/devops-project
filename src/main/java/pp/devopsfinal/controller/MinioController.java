package pp.devopsfinal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pp.devopsfinal.service.MinioService;

@RestController
@RequestMapping("/api/minio")
@RequiredArgsConstructor
public class MinioController {

    private final MinioService minioService;

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadProfilePhotoById(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        String url = minioService.uploadProfilePhoto(
                id,
                file.getOriginalFilename(),
                file.getInputStream(),
                file.getContentType()
        );

        return ResponseEntity.ok(url);
    }

    @GetMapping("/photo-file/{id}")
    public ResponseEntity<InputStreamResource> getProfilePhotoFile(@PathVariable Long id) throws Exception {
        var inputStream = minioService.getProfilePhoto(id);
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .body(resource);
    }
}
