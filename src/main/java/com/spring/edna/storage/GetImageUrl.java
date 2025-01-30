package com.spring.edna.storage;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Service
public class GetImageUrl {

    @Autowired
    private AmazonS3 s3;

    @Value("${cloudfare.bucket.name}")
    private String bucketName;

    public String execute(String imageUrl) {
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000); // 1 hour

        GeneratePresignedUrlRequest presignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, imageUrl)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        URL url = s3.generatePresignedUrl(presignedUrlRequest);

        return url.toString();
    }
}
