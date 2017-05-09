package com.darrel;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Texter {

    private final AmazonSNS amazonSNS;

    public Texter() {
        AmazonSNSClientBuilder builder = AmazonSNSClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain());
        builder.setRegion(System.getenv("Region"));
        amazonSNS = builder.build();
    }


    public void sendSMSMessage() {
       amazonSNS.publish(new PublishRequest()
                .withMessage(getCatFact())
                .withTopicArn(System.getenv("ARN")));
    }

    private String getCatFact() {
        try {
            String url = "http://catfacts-api.appspot.com/api/facts?number=1";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            CatFact catFact = new ObjectMapper().readValue(con.getInputStream(), CatFact.class);

            return catFact.getFacts()[0];
        } catch (IOException e) {
            return "Error";
        }
    }

    public static void main() {
        new Texter().sendSMSMessage();
    }
}
