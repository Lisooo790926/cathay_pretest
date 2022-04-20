package com.pretest.coindesk.services.impl;

import com.pretest.coindesk.data.CoinOutputData;
import com.pretest.coindesk.models.CoinModel;
import com.pretest.coindesk.services.PopulateService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PopulateServiceImpl implements PopulateService {

    private static final String DEFAULT_TYPE = "updated";

    @Value("${cathybank.currency.time.type}")
    private String type;

    @Override
    public Map<String, CoinOutputData> populateToCoinMap(final Map<String, CoinModel> map) {

        final Map<String, CoinOutputData> result = new HashMap<>();
        if (Objects.nonNull(map)) {
            map.forEach((code, coinModel) -> result.put(code, populateToCoinData(coinModel)));
        }
        return result;
    }

    @Override
    public CoinOutputData populateToCoinData(final CoinModel coinModel) {

        if (Objects.isNull(coinModel)) return new CoinOutputData();

        final CoinOutputData data = new CoinOutputData();
        data.setCode(coinModel.getCode());
        Currency currency = Currency.getInstance(coinModel.getCode());
        // Instead of hard code locale, we could use properties or some else way to make it configurable
        data.setName(currency.getDisplayName(Locale.TAIWAN));
        data.setRate(coinModel.getRate());
        return data;
    }

    @Override
    public String populateToUpdatedTime(final Map<String, String> timeData) {
        return Objects.isNull(timeData) ? Strings.EMPTY :
                timeData.getOrDefault(type, timeData.get(DEFAULT_TYPE));
    }

}
