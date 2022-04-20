package com.pretest.coindesk.services.impl;

import com.pretest.coindesk.data.CoinOutputData;
import com.pretest.coindesk.models.CoinModel;
import com.pretest.coindesk.services.PopulateService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.*;

@Service
public class PopulateServiceImpl implements PopulateService {

    private static final String DEFAULT_TYPE = "updated";
    private static final String UK_TYPE = "updateduk";

    private static final Map<String, String> TIME_FORMAT_MAP = new HashMap<>();
    private static final String STANDARD_FORMAT = "yyyy/MM/dd HH:mm:ss";

    static {
        TIME_FORMAT_MAP.put(DEFAULT_TYPE, "MMM dd, yyyy HH:mm:ss z");
        TIME_FORMAT_MAP.put(UK_TYPE, "MMM dd, yyyy 'at' HH:mm z");
    }

    @Value("${cathybank.currency.time.type}")
    private String type;

    @Override
    public Map<String, CoinOutputData> populateToCoinMap(final Map<String, CoinModel> map) {

        final Map<String, CoinOutputData> result = new HashMap<>();
        if (Objects.nonNull(map)) {
            map.forEach((code, coinModel) -> result.put(code, populateToCoinData(coinModel)));
        }
        return result;
    }

    @Override
    public CoinOutputData populateToCoinData(final CoinModel coinModel) {

        if (Objects.isNull(coinModel)) return new CoinOutputData();

        final CoinOutputData data = new CoinOutputData();
        data.setCode(coinModel.getCode());
        Currency currency = Currency.getInstance(coinModel.getCode());
        // Instead of hard code locale, we could use properties or some else way to make it configurable
        data.setName(currency.getDisplayName(Locale.TAIWAN));
        data.setRate(coinModel.getRate());
        return data;
    }

    @Override
    public String populateToUpdatedTime(final Map<String, String> timeData) {

        if (Objects.isNull(timeData)) return Strings.EMPTY;

        final String stringTime = timeData.getOrDefault(type, timeData.get(DEFAULT_TYPE));
        final Temporal temporal;

        if (Strings.isBlank(type) || DEFAULT_TYPE.equals(type) || UK_TYPE.equals(type)) {
            final String format = TIME_FORMAT_MAP.getOrDefault(type, timeData.get(DEFAULT_TYPE));
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            temporal = ZonedDateTime.parse(stringTime, formatter);
        } else {
            temporal = OffsetDateTime.parse(stringTime.replace(" ", "T"));
        }

        final DateTimeFormatter standardFormatter = DateTimeFormatter.ofPattern(STANDARD_FORMAT);
        return standardFormatter.format(temporal);
    }

}
