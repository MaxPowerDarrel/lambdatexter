package com.darrel;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.util.Base64;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

public class Texter {

    private final String phoneNumber;
    private final AmazonSNS amazonSNS;
    private final S3Client s3Client;

    public Texter() {
        this.phoneNumber = decryptKey("PhoneNumber");
        this.s3Client = new S3Client();
        AmazonSNSClientBuilder builder = AmazonSNSClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain());
        builder.setRegion("us-west-2");
        amazonSNS = builder.build();
    }


    public void sendSMSMessage() {
        PublishResult result = amazonSNS.publish(new PublishRequest()
                .withMessage(getCatFact())
                .withPhoneNumber(phoneNumber));
        System.out.println(result); // Prints the message ID.
    }

    private String decryptKey(String key) {
        byte[] encryptedKey = Base64.decode(System.getenv(key));

        AWSKMS client = AWSKMSClientBuilder.defaultClient();

        DecryptRequest request = new DecryptRequest()
                .withCiphertextBlob(ByteBuffer.wrap(encryptedKey));

        ByteBuffer plainTextKey = client.decrypt(request).getPlaintext();
        return new String(plainTextKey.array(), Charset.forName("UTF-8"));
    }

    private String getCatFact() {
        List<String> catFacts = s3Client.getCatFacts();
        return catFacts.get(new Random().nextInt(catFacts.size() -1));
    }

    public static void main() {
        new Texter().sendSMSMessage();
    }
}
