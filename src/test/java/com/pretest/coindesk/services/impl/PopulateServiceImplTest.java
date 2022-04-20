package com.pretest.coindesk.services.impl;

import com.pretest.coindesk.data.CoinOutputData;
import com.pretest.coindesk.models.CoinModel;
import org.apache.logging.log4j.util.Strings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PopulateServiceImplTest {

    private CoinModel coinModel;

    @InjectMocks
    private PopulateServiceImpl populateService;

    @Before
    public void setup() {
        coinModel = new CoinModel();
    }

    @Test
    public void test_populateToCoinData_with_null_input() {

        CoinOutputData coin = populateService.populateToCoinData(null);

        Assert.assertNotNull(coin);
        Assert.assertNull(coin.getCode());
    }

    @Test
    public void test_populateToCoinData_with_correct_input() {

        coinModel.setCode("USD");
        coinModel.setRate("123");
        CoinOutputData data = populateService.populateToCoinData(coinModel);

        Assert.assertNotNull(data);
        Assert.assertEquals("USD", data.getCode());
        Assert.assertEquals("美元", data.getName());
        Assert.assertEquals("123", data.getRate());
    }

    @Test
    public void test_populateToCoinMap_with_null() {

        Map<String, CoinOutputData> result = populateService.populateToCoinMap(null);

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void test_populateToCoinMap_with_correct_input() {

        HashMap<String, CoinModel> map = new HashMap<>();
        map.put("USD", coinModel);
        coinModel.setCode("USD");
        coinModel.setRate("123");
        Map<String, CoinOutputData> result = populateService.populateToCoinMap(map);

        Assert.assertNotNull(result);
        CoinOutputData data = result.get("USD");
        Assert.assertNotNull(data);
        Assert.assertEquals("USD", data.getCode());
        Assert.assertEquals("美元", data.getName());
        Assert.assertEquals("123", data.getRate());
    }

    @Test
    public void test_populateToUpdatedTime_with_null() {
        Assert.assertEquals(Strings.EMPTY, populateService.populateToUpdatedTime(null));
    }

    @Test
    public void test_populateToUpdatedTime_with_correct_input() {

        ReflectionTestUtils.setField(populateService, "type", "test");
        HashMap<String, String> timeMap = new HashMap<>();
        timeMap.put("updated", "defaultTime");
        Assert.assertEquals("defaultTime", populateService.populateToUpdatedTime(timeMap));

        timeMap.put("test", "12/03");
        Assert.assertEquals("12/03", populateService.populateToUpdatedTime(timeMap));
    }

}
