package com.milos.numeric.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GenderService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GenderService(RestTemplateBuilder builder, ObjectMapper objectMapper) {
        this.restTemplate = builder.build();
        this.objectMapper = objectMapper;
    }

    public String determineGender(String name) {
        String uri = "https://api.genderize.io?name=" + name;
        String result = restTemplate.getForObject(uri, String.class);
        try {
            JsonNode node = objectMapper.readTree(result);
            return node.get("gender").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing genderize.io response", e);
        }
    }
}
