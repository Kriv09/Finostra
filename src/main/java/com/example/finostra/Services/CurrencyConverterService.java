package com.example.finostra.Services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyConverterService {


    private static final Map<String, Double> EXCHANGE_RATES = new HashMap<>();

    static {
        // Приклад: базова валюта - USD
        EXCHANGE_RATES.put("USD", 1.0);
        EXCHANGE_RATES.put("EUR", 0.85);
        EXCHANGE_RATES.put("GBP", 0.75);
        EXCHANGE_RATES.put("UAH", 36.5);
    }

    /**
     * Конвертує суму з однієї валюти в іншу.
     *
     * @param amount       Сума для конвертації.
     * @param fromCurrency Валюта, з якої конвертуємо.
     * @param toCurrency   Валюта, в яку конвертуємо.
     * @return Конвертована сума.
     */
    public double convert(double amount, String fromCurrency, String toCurrency) {
        if (!EXCHANGE_RATES.containsKey(fromCurrency) || !EXCHANGE_RATES.containsKey(toCurrency)) {
            throw new IllegalArgumentException("Unsupported currency: " + fromCurrency + " or " + toCurrency);
        }

        double fromRate = EXCHANGE_RATES.get(fromCurrency);
        double toRate = EXCHANGE_RATES.get(toCurrency);

        // Конвертація
        return amount * (toRate / fromRate);
    }

    /**
     * Додає або оновлює курс валют.
     *
     * @param currency Валюта.
     * @param rate     Курс валют щодо базової валюти (USD).
     */
    public void updateExchangeRate(String currency, double rate) {
        EXCHANGE_RATES.put(currency, rate);
    }

    /**
     * Видаляє валюту з курсу.
     *
     * @param currency Валюта для видалення.
     */
    public void removeCurrency(String currency) {
        EXCHANGE_RATES.remove(currency);
    }
}
