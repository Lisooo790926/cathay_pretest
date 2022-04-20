package com.pretest.coindesk.services;

import com.pretest.coindesk.data.CoinDeskApiResponse;

public interface CoinDeskClientService {

    CoinDeskApiResponse fetchCoinDeskByAPI();

}
