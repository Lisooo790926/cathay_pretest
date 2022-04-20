package com.pretest.coindesk.services.impl;

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

    @Value("${cathybank.api.url}")
    private String apiUrl;

    @Override
    public CoinDeskApiResponse fetchCoinDeskByAPI() {
        log.info("Fetching the coin desk by api calling {}", apiUrl);
        try {
            return getRestTemplate().getForObject(apiUrl, CoinDeskApiResponse.class);
        } catch (Exception e) {
            log.error("There is error when fetching the coin desk", e);
            return null;
        }
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
