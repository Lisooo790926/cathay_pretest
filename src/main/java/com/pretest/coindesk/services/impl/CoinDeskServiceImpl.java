package com.pretest.coindesk.services.impl;

import com.pretest.coindesk.data.CoinDeskApiResponse;
import com.pretest.coindesk.data.CoinDeskOutputData;
import com.pretest.coindesk.models.CoinModel;
import com.pretest.coindesk.repo.CoinRepository;
import com.pretest.coindesk.services.CoinDeskClientService;
import com.pretest.coindesk.services.CoinDeskService;
import com.pretest.coindesk.services.PopulateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoinDeskServiceImpl implements CoinDeskService {

    private final CoinDeskClientService coinDeskClientService;
    private final CoinRepository coinRepository;
    private final PopulateService populateService;

    @Override
    public CoinDeskOutputData getCoinDeskResponse() {
        final CoinDeskApiResponse response = getCoinDeskClientService().fetchCoinDeskByAPI();
        final CoinDeskOutputData result = new CoinDeskOutputData();
        if (Objects.nonNull(response)) {
            result.setCoins(getPopulateService().populateToCoinMap(response.getBpi()));
            result.setUpdatedTime(getPopulateService().populateToUpdatedTime(response.getTime()));
            return result;
        }
        return result;
    }

    @Override
    public boolean storeCoinDeskResponse() {
        final CoinDeskApiResponse response = getCoinDeskClientService().fetchCoinDeskByAPI();
        if (Objects.nonNull(response) && Objects.nonNull(response.getBpi())
                && response.getBpi().size() > 0) {
            log.info("Start to saving all coin data {} from api calling", response);
            response.getBpi().forEach((code, coin) -> saveOrUpdate(coin));
            return true;
        }
        return false;
    }

    @Override
    public Optional<CoinModel> save(final CoinModel coinModel) {

        Assert.notNull(coinModel, "Coin should not be null");
        Assert.notNull(coinModel.getCode(), "Code of the coin should not be null");

        if (get(coinModel.getCode()).isPresent()) {
            return Optional.empty();
        }

        log.info("Save the coinModel {}", coinModel.getCode());
        return Optional.of(getCoinRepository().save(coinModel));
    }

    @Override
    public Optional<CoinModel> update(final CoinModel coinModel) {

        Assert.notNull(coinModel, "Coin should not be null");
        Assert.notNull(coinModel.getCode(), "Code of the coin should not be null");

        log.info("Update the coin with code {}", coinModel.getCode());
        final Optional<CoinModel> oCoin = get(coinModel.getCode());
        if (oCoin.isPresent()) {
            updateCoinModel(coinModel, oCoin.get());
            return Optional.of(getCoinRepository().save(oCoin.get()));
        }
        return Optional.empty();
    }

    @Override
    public boolean remove(final String code) {

        log.info("Remove the coin with code {}", code);
        Optional<CoinModel> coinModel = get(code);
        coinModel.ifPresent(coin -> getCoinRepository().delete(coin));
        return coinModel.isPresent();
    }

    @Override
    public Optional<CoinModel> get(final String code) {
        log.info("Getting CoinData by code {}", code);
        return Optional.ofNullable(getCoinRepository().findByCode(code));
    }

    private Optional<CoinModel> saveOrUpdate(final CoinModel coinModel) {
        final Optional<CoinModel> saveModel = save(coinModel);
        return !saveModel.isPresent() ? update(coinModel) : saveModel;
    }

    private void updateCoinModel(final CoinModel updated, final CoinModel original) {
        if (Strings.isNotBlank(updated.getDescription())) original.setDescription(updated.getDescription());
        if (Strings.isNotBlank(updated.getRate())) original.setRate(updated.getRate());
        if (Strings.isNotBlank(updated.getSymbol())) original.setSymbol(updated.getSymbol());
        if (Objects.nonNull(updated.getRate_float())) original.setRate_float(updated.getRate_float());
    }

    public CoinDeskClientService getCoinDeskClientService() {
        return coinDeskClientService;
    }

    public CoinRepository getCoinRepository() {
        return coinRepository;
    }

    public PopulateService getPopulateService() {
        return populateService;
    }
}
