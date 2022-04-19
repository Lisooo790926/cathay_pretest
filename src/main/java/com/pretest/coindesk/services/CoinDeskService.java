package com.pretest.coindesk.services;

import com.pretest.coindesk.data.CoinData;
import com.pretest.coindesk.data.CoinDeskResponse;

public interface CoinDeskService {

    boolean storeCoinDeskResponse(CoinDeskResponse response);

    CoinData save(CoinData coinData);
    CoinData update(CoinData coinData);
    boolean remove(String code);
    CoinData get(String code);

}
