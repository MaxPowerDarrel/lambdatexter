package com.darrel;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Texter {

    private static void sendBuzzMessage(AnimalFactPostBody postBody) {
        try {
            String url = System.getenv("URL");
            URL obj = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String postData = new ObjectMapper().writeValueAsString(postBody);
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData.getBytes());
            }
            connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static AnimalFactPostBody getAnimalFact() {
        try {
            int random = new Random().nextInt(2);
            String url = random == 0 ? "http://catfacts-api.appspot.com/api/facts?number=1" : "https://dog-api.kinduff.com/api/facts?number=1";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            AnimalFact animalFact = new ObjectMapper().readValue(con.getInputStream(), AnimalFact.class);

            return new AnimalFactPostBody(random == 0 ? "Cat Fact" : "Dog Fact", animalFact.getFacts()[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main() {
        sendBuzzMessage(getAnimalFact());
    }
}
