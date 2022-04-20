package com.pretest.coindesk.controllers;

import com.pretest.coindesk.data.CoinDeskApiResponse;
import com.pretest.coindesk.data.CoinDeskOutputData;
import com.pretest.coindesk.models.CoinModel;
import com.pretest.coindesk.services.CoinDeskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/coin")
public class CoinDeskController {

    private static CoinModel dummy = new CoinModel();

    static {
        dummy.setDescription("Not successful");
    }

    @Resource
    private CoinDeskService coinDeskService;

    @GetMapping("/api/all")
    public CoinDeskApiResponse getApiResponse() {
        return coinDeskService.getWholeCoinDeskResponse();
    }

    @GetMapping("/api")
    public CoinDeskOutputData getApiMinResponse() {
        return coinDeskService.getCoinDeskResponse();
    }

    @PostMapping("/api/store")
    public boolean storeApiResponse() {
        return coinDeskService.storeCoinDeskResponse();
    }

    @PostMapping("/save")
    public CoinModel save(@RequestBody final CoinModel coinModel) {
        return coinDeskService.save(coinModel).orElse(dummy);
    }

    @PutMapping("/update")
    public CoinModel update(@RequestBody final CoinModel coinModel) {
        return coinDeskService.update(coinModel).orElse(dummy);
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestParam final String code) {
        return coinDeskService.remove(code);
    }

    @GetMapping("/get")
    public CoinModel get(@RequestParam final String code) {
        return coinDeskService.get(code).orElse(dummy);
    }

}
