package com.darrel;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Texter {

    private static void sendBuzzMessage(String message) {
        try {
            String url = System.getenv("URL");
            URL obj = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            CatFactPostBody catFactPostBody = new CatFactPostBody(message);
            String postData = new ObjectMapper().writeValueAsString(catFactPostBody);
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData.getBytes());
            }
            connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCatFact() {
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
        sendBuzzMessage(getCatFact());
    }
}
