package com.pretest.coindesk.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinDeskOutputData {

    private String updatedTime;
    private Map<String, CoinOutputData> coins;
}
