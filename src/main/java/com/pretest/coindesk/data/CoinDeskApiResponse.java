package com.pretest.coindesk.data;

import com.pretest.coindesk.models.CoinModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinDeskApiResponse {

    private Map<String, String> time;
    private String disclaimer;
    private String chartName;
    private Map<String, CoinModel> bpi;

}
