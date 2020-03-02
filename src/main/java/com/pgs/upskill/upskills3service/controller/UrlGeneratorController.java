package com.pgs.upskill.upskills3service.controller;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.pgs.upskill.upskills3service.config.S3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.Date;

@RestController
@RequestMapping("urlgenerator")
public class UrlGeneratorController {

    AmazonS3 s3Client;

    @Autowired
    public UrlGeneratorController(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @GetMapping
    @RequestMapping("/{operation}/{object_key}")
    public String getWriteUrl(@PathVariable("operation") String operation,
                              @PathVariable("object_key") String objectKey) {

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(S3Config.BUCKET_KEY, objectKey)
                        .withMethod(getHttpMethod(operation))
                        .withExpiration(getExpiration());
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    private HttpMethod getHttpMethod(String operation) {
        switch (operation) {
            case "read":
                return HttpMethod.GET;
            case "write":
                return HttpMethod.PUT;
            default:
                throw new RuntimeException("Unsupported operation: " + operation);
        }
    }

    private Date getExpiration() {
        // Set the presigned URL to expire after one hour.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);
        return expiration;
    }
}
