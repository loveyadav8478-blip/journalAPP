package com.Origin.journalAPP.service;

import com.Origin.journalAPP.api.response.WeatherResponse;
import com.Origin.journalAPP.cache.AppCache;
import com.Origin.journalAPP.constant.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;
    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("Weather_of_" + city, WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        }
        else{
            String finalApi = appCache.APP_CACHE.get(AppCache.key.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            response.getStatusCode();
            WeatherResponse body = response.getBody();
            if(body != null){
                redisService.set("Weather of " + city, body,10000l);
            }
            return body;
        }

    }

}
