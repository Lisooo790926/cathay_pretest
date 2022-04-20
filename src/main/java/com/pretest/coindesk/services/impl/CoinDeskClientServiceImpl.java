package com.pretest.coindesk.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretest.coindesk.data.CoinDeskApiResponse;
import com.pretest.coindesk.services.CoinDeskClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoinDeskClientServiceImpl implements CoinDeskClientService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${cathybank.api.url}")
    private String apiUrl;

    @Override
    public CoinDeskApiResponse fetchCoinDeskByAPI() {
        log.info("Fetching the coin desk by api calling {}", apiUrl);
        try {
            final String response = getRestTemplate().getForObject(apiUrl, String.class);
            return getObjectMapper().readValue(response, CoinDeskApiResponse.class);
        } catch (Exception e) {
            log.error("There is error when fetching the coin desk", e);
            return null;
        }
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
