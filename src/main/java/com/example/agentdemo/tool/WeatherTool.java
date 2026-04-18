package com.example.agentdemo.tool;

import com.example.agentdemo.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class WeatherTool {

    private static final Logger logger = LoggerFactory.getLogger(WeatherTool.class);
    private static final String WEATHER_API_URL = "https://restapi.amap.com/v3/weather/weatherInfo";

    @Value("${gaode.weather.api-key}")
    private String apiKey;

    @Tool(description = "获取某个城市的天气，参数为城市名称（如北京、上海）")
    public String getWeather(String city) {
        if (city == null || city.trim().isEmpty()) {
            return "城市名称不能为空，请提供有效的城市名称";
        }

        String encodedCity;
        try {
            encodedCity = URLEncoder.encode(city.trim(), "UTF-8");
        } catch (Exception e) {
            logger.error("城市名称编码失败: {}", city, e);
            return "城市名称编码失败，请稍后重试";
        }

        String url = WEATHER_API_URL + "?key=" + apiKey + "&city=" + encodedCity + "&extensions=base&output=JSON";
        logger.info("正在查询天气: {}，URL: {}", city, url);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            logger.info("天气API响应: {}", responseBody);

            return HttpUtil.parseWeatherResponse(responseBody, city);

        } catch (Exception e) {
            logger.error("查询天气失败: {}", city, e);
            return "查询天气时出现网络错误，请稍后重试";
        }
    }


}
