package com.pgs.upskill.upskills3service.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.pgs.upskill.upskills3service.config.S3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController {

    AmazonS3 s3Client;

    @Autowired
    public FileController(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @GetMapping
    public List<String> getFiles() {
        ListObjectsV2Result objects = s3Client.listObjectsV2(S3Config.BUCKET_KEY);
        return objects.getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }
}
