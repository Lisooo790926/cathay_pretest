package com.pretest.coindesk.services.impl;

import com.pretest.coindesk.data.CoinDeskApiResponse;
import com.pretest.coindesk.data.CoinDeskOutputData;
import com.pretest.coindesk.data.CoinOutputData;
import com.pretest.coindesk.models.CoinModel;
import com.pretest.coindesk.repo.CoinRepository;
import com.pretest.coindesk.services.CoinDeskClientService;
import com.pretest.coindesk.services.PopulateService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CoinDeskServiceImplTest {

    private CoinDeskApiResponse response;
    private CoinModel model;

    @Mock
    private CoinDeskClientService coinDeskClientService;
    @Mock
    private CoinRepository coinRepository;
    @Mock
    private PopulateService populateService;

    @InjectMocks
    private CoinDeskServiceImpl coinDeskService;

    @Before
    public void setup() {
        response = new CoinDeskApiResponse();
        model = new CoinModel();
    }

    @Test
    public void test_getCoinDeskResponse_with_clientService_null_response() {
        Mockito.when(coinDeskClientService.fetchCoinDeskByAPI()).thenReturn(null);
        CoinDeskOutputData result = coinDeskService.getCoinDeskResponse();

        Assert.assertNotNull(result);
        Assert.assertNull(result.getCoins());
    }

    @Test
    public void test_getCoinDeskResponse_with_response() {

        response.setBpi(new HashMap<>());
        response.setTime(new HashMap<>());

        Map<String, CoinOutputData> populateCoinMap = new HashMap<>();
        CoinOutputData coin = new CoinOutputData();
        coin.setCode("testCode");
        populateCoinMap.put("testCoin", coin);

        Mockito.when(coinDeskClientService.fetchCoinDeskByAPI()).thenReturn(response);
        Mockito.when(populateService.populateToCoinMap(Mockito.anyMap())).thenReturn(populateCoinMap);
        Mockito.when(populateService.populateToUpdatedTime(Mockito.anyMap())).thenReturn("time_format");

        CoinDeskOutputData result = coinDeskService.getCoinDeskResponse();

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getCoins());
        Assert.assertEquals(1, result.getCoins().size());
        CoinOutputData testCoin = result.getCoins().get("testCoin");
        Assert.assertNotNull(testCoin);
        Assert.assertEquals("testCode", testCoin.getCode());
    }

    @Test
    public void test_storeCoinDeskResponse_with_clientService_null_response() {
        Mockito.when(coinDeskClientService.fetchCoinDeskByAPI()).thenReturn(null);
        Assert.assertFalse(coinDeskService.storeCoinDeskResponse());
    }

    @Test
    public void test_storeCoinDeskResponse_with_response_but_no_bpi() {
        Mockito.when(coinDeskClientService.fetchCoinDeskByAPI()).thenReturn(response);
        Assert.assertFalse(coinDeskService.storeCoinDeskResponse());
    }

    @Test
    public void test_storeCoinDeskResponse_with_response_but_bpi_empty() {
        response.setBpi(new HashMap<>());
        Mockito.when(coinDeskClientService.fetchCoinDeskByAPI()).thenReturn(response);
        Assert.assertFalse(coinDeskService.storeCoinDeskResponse());
    }

    @Test
    public void test_storeCoinDeskResponse_with_correct_response() {
        response.setBpi(new HashMap<>());
        response.getBpi().put("test", model);
        model.setCode("test");
        Mockito.when(coinDeskClientService.fetchCoinDeskByAPI()).thenReturn(response);
        Mockito.when(coinRepository.findByCode("test")).thenReturn(null);
        Mockito.when(coinRepository.save(model)).thenReturn(model);
        Assert.assertTrue(coinDeskService.storeCoinDeskResponse());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_save_with_null_input() {
        coinDeskService.save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_save_with_null_code() {
        coinDeskService.save(model);
    }

    @Test
    public void test_save_with_existed_model() {
        model.setCode("code");
        Mockito.when(coinRepository.findByCode("code")).thenReturn(model);
        Assert.assertFalse(coinDeskService.save(model).isPresent());
    }

    @Test
    public void test_save_successfully() {
        model.setCode("code");
        Mockito.when(coinRepository.findByCode("code")).thenReturn(null);
        Mockito.when(coinRepository.save(model)).thenReturn(model);
        Optional<CoinModel> result = coinDeskService.save(model);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("code", result.get().getCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_update_with_null_input() {
        coinDeskService.update(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_update_with_null_code() {
        coinDeskService.update(model);
    }

    @Test
    public void test_update_with_not_existed_model() {
        model.setCode("code");
        Mockito.when(coinRepository.findByCode("code")).thenReturn(null);
        Assert.assertFalse(coinDeskService.update(model).isPresent());
    }

    @Test
    public void test_update_successfully() {
        model.setCode("code");
        model.setDescription("description");
        model.setRate("1234");
        model.setSymbol("symbol");
        model.setRate_float(123d);

        Mockito.when(coinRepository.findByCode("code")).thenReturn(model);
        Mockito.when(coinRepository.save(model)).thenReturn(model);

        Optional<CoinModel> result = coinDeskService.update(model);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("description", result.get().getDescription());
        Assert.assertEquals("1234", result.get().getRate());
        Assert.assertEquals("symbol", result.get().getSymbol());
        Assert.assertEquals(Double.valueOf(123d), result.get().getRate_float());
    }

    @Test
    public void test_remove_with_null_input_and_not_existed_coin() {
        Assert.assertFalse(coinDeskService.remove(null));

        Mockito.when(coinRepository.findByCode("test")).thenReturn(null);
        Assert.assertFalse(coinDeskService.remove("test"));
    }

    @Test
    public void test_remove_successfully() {
        Mockito.when(coinRepository.findByCode("test")).thenReturn(model);
        Assert.assertTrue(coinDeskService.remove("test"));
    }

    @Test
    public void test_get_with_not_exited_coin() {
        Mockito.when(coinRepository.findByCode("test")).thenReturn(null);
        Assert.assertFalse(coinDeskService.get("test").isPresent());
    }

    @Test
    public void test_get_with_exited_coin() {
        Mockito.when(coinRepository.findByCode("test")).thenReturn(model);
        Assert.assertTrue(coinDeskService.get("test").isPresent());
    }

}
