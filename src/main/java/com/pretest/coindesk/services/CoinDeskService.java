package com.pretest.coindesk.services;

import com.pretest.coindesk.data.CoinDeskOutputData;
import com.pretest.coindesk.data.CoinOutputData;
import com.pretest.coindesk.models.CoinModel;

import java.util.Optional;

public interface CoinDeskService {

    CoinDeskOutputData getCoinDeskResponse();
    boolean storeCoinDeskResponse();

    Optional<CoinModel> save(CoinModel coinData);
    Optional<CoinModel> update(CoinModel coinData);
    boolean remove(String code);
    Optional<CoinModel> get(String code);
}
