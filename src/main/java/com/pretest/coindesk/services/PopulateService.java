package com.pretest.coindesk.services;

import com.pretest.coindesk.data.CoinOutputData;
import com.pretest.coindesk.models.CoinModel;

import java.util.Map;

public interface PopulateService {

    Map<String, CoinOutputData> populateToCoinMap(final Map<String, CoinModel> map);
    CoinOutputData populateToCoinData(final CoinModel coinModel);
    /**
     * For populate the specific time format for specific purpose
     *
     * @param timeData map from api response
     * @return time format
     */
    String populateToUpdatedTime(final Map<String, String> timeData);

}
