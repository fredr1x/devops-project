package pp.devopsfinal.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pp.devopsfinal.properties.MinioProperties;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    private final UserService userService;

    public String uploadProfilePhoto(Long id, String fileName, InputStream data, String contentType) throws Exception {
        var user = userService.getUser(id);

        if (user.getPhotoUrl() != null) {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(user.getPhotoUrl())
                    .build());
        }

        String objectName = user.getId() + "/" + fileName;
        userService.savePhotoUrl(user, objectName);
        return upload(minioProperties.getBucket(), objectName, data, contentType);
    }

    public InputStream getProfilePhoto(Long id) throws Exception {
        var user = userService.getUser(id);
        if (user.getPhotoUrl() == null) {
            throw new RuntimeException("User has no profile photo");
        }

        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .object(user.getPhotoUrl())
                        .build()
        );
    }

    private String upload(String bucket, String fileName, InputStream data, String contentType) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .stream(data, data.available(), -1)
                        .contentType(contentType)
                        .build()
        );
        return String.format("%s/%s/%s", minioProperties.getUrl(), bucket, fileName);
    }
}
