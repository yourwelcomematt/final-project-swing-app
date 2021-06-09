package com.swingapp;

import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class API {
    private static API instance;

    private static final String BASE_URL = "http://localhost:3000/api";

    public static API getInstance() {
        if (instance == null) {
            instance = new API();
        }
        return instance;
    }

    private final CookieManager cookieManager;
    private final HttpClient client;

    private API() {
        this.cookieManager = new CookieManager();

        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NEVER)
                .connectTimeout(Duration.ofSeconds(10))
                .cookieHandler(this.cookieManager)
                .build();
    }

//    public Pokemon getRandomPokemon() throws IOException, InterruptedException {
//        HttpRequest.Builder builder = HttpRequest.newBuilder()
//                .uri(URI.create(BASE_URL + "/random"))
//                .setHeader("Accept", "application/json")
//                .method("GET", HttpRequest.BodyPublishers.noBody());
//
//        HttpRequest request = builder.build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        String json = response.body();
//
//
//        return JSONUtils.toObject(json, Pokemon.class);
//    }
//
//    public Pokemon getRandomPokemonOfType(TypeQuery typeQuery) throws IOException, InterruptedException {
//
//        String json = JSONUtils.toJSON(typeQuery);
//
//        HttpRequest.Builder builder = HttpRequest.newBuilder()
//                .uri(URI.create(BASE_URL + "/random"))
//                .setHeader("Content-Type", "application/json")
//                .setHeader("Accept", "application/json")
//                .method("POST", HttpRequest.BodyPublishers.ofString(json));
//
//        HttpRequest request = builder.build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        String responseJson = response.body();
//
//
//        return JSONUtils.toObject(responseJson, Pokemon.class);
//    }

}
