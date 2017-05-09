package com.darrel;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import java.util.List;
import java.util.Random;

public class Texter {

    private final AmazonSNS amazonSNS;
    private final S3Client s3Client;

    public Texter() {
        this.s3Client = new S3Client();
        AmazonSNSClientBuilder builder = AmazonSNSClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain());
        builder.setRegion("us-west-2");
        amazonSNS = builder.build();
    }


    public void sendSMSMessage() {
        PublishResult result = amazonSNS.publish(new PublishRequest()
                .withMessage(getCatFact())
                .withTopicArn("arn:aws:sns:us-west-2:664598357105:CatFacts"));
    }

    private String getCatFact() {
        List<String> catFacts = s3Client.getCatFacts();
        return catFacts.get(new Random().nextInt(catFacts.size() -1));
    }

    public static void main() {
        new Texter().sendSMSMessage();
    }
}
