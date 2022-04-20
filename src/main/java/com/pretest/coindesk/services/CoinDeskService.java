package com.pretest.coindesk.services;

import com.pretest.coindesk.data.CoinDeskApiResponse;
import com.pretest.coindesk.data.CoinDeskOutputData;
import com.pretest.coindesk.models.CoinModel;

import java.util.Optional;

public interface CoinDeskService {

    CoinDeskApiResponse getWholeCoinDeskResponse();

    CoinDeskOutputData getCoinDeskResponse();

    boolean storeCoinDeskResponse();

    Optional<CoinModel> save(CoinModel coinData);

    Optional<CoinModel> update(CoinModel coinData);

    boolean remove(String code);

    Optional<CoinModel> get(String code);
}
