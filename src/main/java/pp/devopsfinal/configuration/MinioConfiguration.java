package pp.devopsfinal.configuration;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.devopsfinal.properties.MinioProperties;



@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfiguration {

    private final MinioProperties properties;

    @Bean
    public MinioClient minioClient() {
        MinioClient client = MinioClient.builder()
                .endpoint(properties.getUrl())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();

        try {
            boolean exists = client.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(properties.getBucket())
                            .build()
            );

            if (!exists) {
                client.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(properties.getBucket())
                                .build()
                );
                log.info("Bucket '{}' created", properties.getBucket());
            } else {
                log.info("Bucket '{}' already exists", properties.getBucket());
            }
        } catch (Exception e) {
            log.error("Error while checking/creating bucket: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return client;
    }
}
