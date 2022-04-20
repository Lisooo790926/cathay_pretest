package com.pretest.coindesk.services;

import com.pretest.coindesk.data.CoinOutputData;
import com.pretest.coindesk.models.CoinModel;

import java.util.Map;

public interface PopulateService {

    Map<String, CoinOutputData> populateToCoinMap(final Map<String, CoinModel> map);

    CoinOutputData populateToCoinData(final CoinModel coinModel);

    String populateToUpdatedTime(final Map<String, String> timeData);

}
