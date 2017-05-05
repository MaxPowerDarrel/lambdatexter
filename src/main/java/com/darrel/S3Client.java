package com.darrel;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class S3Client {

    private final AmazonS3 client;
    private final String bucket = "feheroes";

    public S3Client() {
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain());
        builder.setRegion("us-west-2");
        client = builder.build();
    }

    public List<String> getCatFacts() {
        final S3Object catFactsStream = client.getObject(bucket, "catfacts.txt");
        return new BufferedReader(new InputStreamReader(catFactsStream.getObjectContent())).lines().collect(Collectors.toList());
    }
}

