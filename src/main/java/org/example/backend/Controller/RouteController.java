package org.example.backend.Controller;

import org.example.backend.Entity.JourneyData;
import org.example.backend.Entity.Journey;
import org.example.backend.Entity.Route;
import org.example.backend.utils.JourneyConverter;
import org.example.backend.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class RouteController {

    @Autowired
    private JourneyConverter journeyConverter;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/route")
    public ResponseEntity<Response<List<JourneyData>>> processRoute(@RequestBody Route route, @RequestParam("prediction_step") String predictionStep) {
        System.out.println("Received route and prediction step");

        // 调用 JourneyConverter 将 Route 转换为 Journey
        Journey journeyWithCoordinates = journeyConverter.convertToJourney(route);

        // 构建要发送的 JSON 数据
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构建请求体
        Map<String, Object> requestBody = Map.of(
                "journey", journeyWithCoordinates,
                "prediction_step", predictionStep
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        // 将 Journey 对象发送到 Python 端的 URL
        String pythonEndpoint = "http://127.0.0.1:5000/process_journey";  // Python 服务器地址

        ResponseEntity<List> response;
        try {
            response = restTemplate.postForEntity(pythonEndpoint, request, List.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(500, "Failed to fetch data from Python service"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(500, "Failed to fetch data from Python service"));
        }

        // 输出从 Python 服务接收到的 JSON 数据
        List<Map<String, Object>> jsonData = response.getBody();
        System.out.println("Received response from Python service: " + jsonData);

        List<JourneyData> journeyDataList = parseJsonData(jsonData);

        return ResponseEntity.ok(Response.success(journeyDataList));
    }

    private List<JourneyData> parseJsonData(List<Map<String, Object>> jsonData) {
        List<JourneyData> journeyDataList = new ArrayList<>();
        for (Map<String, Object> record : jsonData) {
            JourneyData data = new JourneyData();
            data.setLatitude(Double.parseDouble(record.get("latitude").toString()));
            data.setLongitude(Double.parseDouble(record.get("longitude").toString()));
            data.setPred0(Double.parseDouble(record.get("pred_0").toString()));
            data.setLocation(record.get("location").toString()); // 设置地点信息
            journeyDataList.add(data);
        }
        return journeyDataList;
    }
}
