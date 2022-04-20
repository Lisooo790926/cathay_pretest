package com.pretest.coindesk.utils;

import java.util.Currency;
import java.util.Locale;

public class CurrencyUtils {

    public static String transferToChinese(String isoCode) {
        final Currency currency = Currency.getInstance(isoCode);
        return currency.getDisplayName(Locale.TAIWAN);
    }
}
