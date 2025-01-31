package com.spring.edna.storage;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeleteImageFromR2 {

    @Autowired
    private AmazonS3 s3;

    @Value("${cloudfare.bucket.name}")
    private String bucketName;

    public HttpStatus execute(String imageUrl) {
        s3.deleteObject(bucketName, imageUrl);

        return HttpStatus.OK;
    }
}
