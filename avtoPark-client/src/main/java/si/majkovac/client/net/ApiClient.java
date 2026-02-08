package si.majkovac.client.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    private final String baseUrl;
    private final HttpClient http;
    private final ObjectMapper om;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.http = HttpClient.newHttpClient();
        this.om = new ObjectMapper();
        this.om.registerModule(new JavaTimeModule());
    }

    public <T> T get(String path, Class<T> clazz) {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .GET()
                .header("Accept", "application/json")
                .build();

        return send(req, clazz);
    }

    public <T> T get(String path, TypeReference<T> typeRef) {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .GET()
                .header("Accept", "application/json")
                .build();

        return send(req, typeRef);
    }

    public void delete(String path) {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .DELETE()
                .header("Accept", "application/json")
                .build();

        send(req);
    }

    public <T> T post(String path, Object body, Class<T> clazz) {
        try {
            String json = om.writeValueAsString(body);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .build();

            return send(req, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void postNoBody(String path, Object body) {
        try {
            String json = om.writeValueAsString(body);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .build();
            send(req);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void put(String path, Object body) {
        try {
            String json = om.writeValueAsString(body);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .build();
            send(req);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void send(HttpRequest req) {
        try {
            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() >= 400) {
                throw new ApiException(res.statusCode(), res.body());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T send(HttpRequest req, Class<T> clazz) {
        try {
            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() >= 400) throw new ApiException(res.statusCode(), res.body());
            if (clazz == Void.class) return null;
            return om.readValue(res.body(), clazz);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T send(HttpRequest req, TypeReference<T> typeRef) {
        try {
            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() >= 400) throw new ApiException(res.statusCode(), res.body());
            return om.readValue(res.body(), typeRef);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
