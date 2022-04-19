package com.pretest.coindesk.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinDeskResponse {

    private CallTimeData time;
    private String disclaimer;
    private String chartName;
    private Map<String, CoinData> bpi;

}
