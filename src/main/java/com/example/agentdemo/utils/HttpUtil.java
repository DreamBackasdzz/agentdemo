package com.example.agentdemo.utils;

import com.example.agentdemo.tool.WeatherTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(WeatherTool.class);
    public static String parseWeatherResponse(String responseBody, String queryCity) {
        try {
            if (responseBody == null || responseBody.isEmpty()) {
                return "获取天气信息失败，请稍后重试";
            }

            com.fasterxml.jackson.databind.JsonNode root = new com.fasterxml.jackson.databind.ObjectMapper().readTree(responseBody);

            String status = root.path("status").asText();
            if (!"1".equals(status)) {
                String info = root.path("info").asText();
                logger.warn("API返回错误: {}", info);
                return "获取天气信息失败：" + info;
            }

            com.fasterxml.jackson.databind.JsonNode lives = root.path("lives");
            if (lives.isArray() && lives.size() > 0) {
                com.fasterxml.jackson.databind.JsonNode live = lives.get(0);
                String province = live.path("province").asText();
                String city = live.path("city").asText();
                String weather = live.path("weather").asText();
                String temperature = live.path("temperature").asText();
                String winddirection = live.path("winddirection").asText();
                String windpower = live.path("windpower").asText();
                String humidity = live.path("humidity").asText();
                String reporttime = live.path("reporttime").asText();

                return String.format(
                        "%s%s当前天气：%s\n温度：%s°C\n湿度：%s%%\n风向：%s风\n风力：%s级\n数据更新时间：%s",
                        province, city, weather, temperature, humidity, winddirection, windpower, reporttime
                );
            }

            return "未找到该城市的天气信息，请确认城市名称是否正确";

        } catch (Exception e) {
            logger.error("解析天气响应失败: {}", responseBody, e);
            return "解析天气信息失败，请稍后重试";
        }
    }
}
