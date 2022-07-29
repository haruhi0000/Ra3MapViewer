package com.haruhi.ra3mapviewer.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haruhi.ra3mapviewer.common.ApiResult;
import com.haruhi.ra3mapviewer.entity.Ra3Map;
import com.haruhi.ra3mapviewer.exception.FetchException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haruhi0000
 */
public class AppHttpClient {
    public AppHttpClient() {
    }

    public static List<Ra3Map> fetchRa3Maps() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        List<Ra3Map> ra3Maps = new ArrayList<>();
        try {
            HttpResponse<String> httpResponse = httpClient.send(HttpRequest.newBuilder().GET()
                    .uri(URI.create("http://127.0.0.1:8080/map/list")).build(), bodyHandler);
            System.out.println(httpResponse.body());
            ApiResult<List<Ra3Map>> apiResult;
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(httpResponse.body());
            JsonNode dataJsonNode = jsonNode.get("data");
            ra3Maps = new ArrayList<>(dataJsonNode.size());
            for (JsonNode node : dataJsonNode) {
                Ra3Map ra3Map = objectMapper.readValue(node.toString(), Ra3Map.class);
                ra3Maps.add(ra3Map);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("连接服务器失败");
        }
        return ra3Maps;
    }
    public static byte[] fetchStaticFile(String filePath) throws FetchException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<byte[]> bodyHandler = HttpResponse.BodyHandlers.ofByteArray();
        try {
            HttpRequest.BodyPublisher bodyPublisher
                    = HttpRequest.BodyPublishers.ofString("filePath=" + filePath, StandardCharsets.UTF_8);
            HttpResponse<byte[]> httpResponse = httpClient.send(HttpRequest.newBuilder().POST(bodyPublisher)
                    .uri(URI.create("http://127.0.0.1:8080/map/file"))
                    .header("Content-type", "application/x-www-form-urlencoded")
                    .build(), bodyHandler);
            if(httpResponse.statusCode() == 200) {
                return httpResponse.body();
            } else {
                throw new FetchException("获取资源失败");
            }
        } catch (IOException | InterruptedException e) {
            throw new FetchException(e.getMessage());
        }
    }
}
