package service;

import model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public interface ExchangeService {
    void putExchangeRate(LocalDate requestedDate, String currencyCode, BigDecimal buyRate, BigDecimal sellRate);

    void removeExchangeRate(LocalDate requestedDate, String currencyCode);

    Set<CurrencyRate> listExchangeRatesHistory(LocalDate requestedDate);

    BigDecimal exchange(LocalDate requestedDate, BigDecimal sum,
                        CurrencyRate initialCurrencyCode, CurrencyRate aimCurrencyCode);
}
