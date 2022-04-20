package com.pretest.coindesk.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretest.coindesk.data.CoinDeskApiResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CoinDeskClientServiceImplTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CoinDeskClientServiceImpl coinDeskClientService;

    @Before
    public void setup(){
        ReflectionTestUtils.setField(coinDeskClientService, "apiUrl", "test");
    }

    @Test
    public void test_fetchCoinDeskByAPI_with_RestClientException() {

        Mockito.when(restTemplate.getForObject("test", String.class))
                .thenThrow(new RestClientException("test"));
        Assert.assertNull(coinDeskClientService.fetchCoinDeskByAPI());
    }

    @Test
    public void test_fetchCoinDeskByAPI_JsonProcessingException() throws JsonProcessingException {

        Mockito.when(restTemplate.getForObject("test", String.class))
                .thenReturn("test");
        Mockito.when(objectMapper.readValue("test", CoinDeskApiResponse.class))
                .thenThrow(new JsonMappingException("test"));

        Assert.assertNull(coinDeskClientService.fetchCoinDeskByAPI());
    }

    @Test
    public void test_fetchCoinDeskByAPI_successfully_fetch_data() throws JsonProcessingException {

        CoinDeskApiResponse coinDeskApiResponse = new CoinDeskApiResponse();
        coinDeskApiResponse.setChartName("test");

        Mockito.when(restTemplate.getForObject("test", String.class))
                .thenReturn("test");
        Mockito.when(objectMapper.readValue("test", CoinDeskApiResponse.class))
                .thenReturn(coinDeskApiResponse);
        CoinDeskApiResponse response = coinDeskClientService.fetchCoinDeskByAPI();

        Assert.assertNotNull(response);
        Assert.assertEquals("test", response.getChartName());
    }

}
