package org.example.backend.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.backend.Entity.Journey;
import org.example.backend.Entity.Route;
import org.springframework.stereotype.Component;

@Component
public class JourneyConverter {

    private static final String API_KEY = "a7997da61bccd4b31193c094f3b832ef";

    public Journey convertToJourney(Route route) {
        Journey journey = new Journey();

        try {
            // 转换起点
            String startLocation = geocode(route.getStart());
            if (startLocation != null) {
                String[] startCoords = startLocation.split(",");
                journey.setStartLatitude(Double.parseDouble(startCoords[1]));
                journey.setStartLongitude(Double.parseDouble(startCoords[0]));
            }

            // 转换终点
            String endLocation = geocode(route.getEnd());
            if (endLocation != null) {
                String[] endCoords = endLocation.split(",");
                journey.setEndLatitude(Double.parseDouble(endCoords[1]));
                journey.setEndLongitude(Double.parseDouble(endCoords[0]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return journey;
    }

    private String geocode(String address) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String url = "https://restapi.amap.com/v3/geocode/geo?address=" + address + "&key=" + API_KEY;
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to get response");
            }

            String responseBody = response.body().string();
            JSONObject jsonObject = JSON.parseObject(responseBody);
            if (jsonObject.getJSONArray("geocodes") != null && !jsonObject.getJSONArray("geocodes").isEmpty()) {
                String location = jsonObject.getJSONArray("geocodes").getJSONObject(0).getString("location");
                return location;
            }
        }
        return null;
    }
}
