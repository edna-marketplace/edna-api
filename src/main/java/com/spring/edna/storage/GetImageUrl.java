package com.spring.edna.storage;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GetImageUrl {

    @Autowired
    private AmazonS3 s3;

    @Value("${cloudfare.bucket.name}")
    private String bucketName;

    private final Map<String, CachedPresignedUrl> urlCache = new ConcurrentHashMap<>();

    private static final long CACHE_EXPIRATION_MS = 60 * 60 * 1000; // 1 hora

    private static final long BUFFER_TIME_MS = 5 * 60 * 1000; // 5 minutos

    public String execute(String imageUniqueName) {

        if (imageUniqueName == null || imageUniqueName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da imagem não pode ser nulo ou vazio");
        }

        CachedPresignedUrl cachedUrl = urlCache.get(imageUniqueName);
        long currentTime = System.currentTimeMillis();

        if (cachedUrl != null && cachedUrl.getExpirationTime() < currentTime) {
            urlCache.remove(imageUniqueName);
            cachedUrl = null;
        }

        if(cachedUrl != null && (cachedUrl.getExpirationTime() - currentTime) > BUFFER_TIME_MS) {
            return cachedUrl.getUrl();
        }

        String newUrl = generateUrl(imageUniqueName);

        urlCache.put(imageUniqueName, new CachedPresignedUrl(newUrl, currentTime + CACHE_EXPIRATION_MS));

        return newUrl;
    }

    private String generateUrl(String imageUniqueName) {
        Date expiration = new Date(System.currentTimeMillis() + CACHE_EXPIRATION_MS); // 1 hour

        GeneratePresignedUrlRequest presignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, imageUniqueName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        URL url = s3.generatePresignedUrl(presignedUrlRequest);

        return url.toString();
    }

    @AllArgsConstructor
    @Data
    private static class CachedPresignedUrl {
        final String url;
        final long expirationTime;
    }
}
