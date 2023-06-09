package service;

import model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public interface ExchangeService {
    void putExchangeRate(LocalDate requestedDate, Currency currency, BigDecimal buyRate, BigDecimal
            sellRate);

    void removeExchangeRate(LocalDate requestedDate, String currencyCode);

    List<CurrencyRate> listExchangeRatesHistory(LocalDate requestedDate);

    BigDecimal exchange(LocalDate requestedDate, BigDecimal sum,
                        String initialCurrencyCode, String aimCurrencyCode);

}
