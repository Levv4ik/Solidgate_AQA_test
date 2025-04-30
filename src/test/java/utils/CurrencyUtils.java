package utils;

import java.util.Map;

public class CurrencyUtils {
    private static final Map<String, String> symbolToCodeMap = Map.of(
            "€", "EUR",
            "$", "USD",
            "£", "GBP"
    );

    public static String getCurrencyCodeFromSymbol(String symbol) {
        return symbolToCodeMap.get(symbol);
    }
}

