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

    /**
     * Converts the LoginQueryObject to a JSON string and sends a HTTP POST request to
     * /api/login - if a 204 success response code is received, return true, else return false
     */
    public Boolean authenticateUser(LoginQuery loginQuery) throws IOException, InterruptedException {

        String json = JSONUtils.toJSON(loginQuery);

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/login"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(json));

        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int responseCode = response.statusCode();

        if (responseCode == 204) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sends a GET request to /api/users and converts the retrieved JSON string into a list
     * and returns it
     */
    public List<User> getUserList() throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users"))
                .setHeader("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody());

        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        return JSONUtils.toList(json, User.class);
    }

    /**
     * Sends a GET request to /api/logout
     */
    public void logoutUser() throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/logout"))
                .setHeader("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody());

        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Sends a DELETE request to /api/users and appends the id parameter to the end
     */
    public void deleteUser(Object id) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/" + id))
                .setHeader("Accept", "application/json")
                .method("DELETE", HttpRequest.BodyPublishers.noBody());

        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
