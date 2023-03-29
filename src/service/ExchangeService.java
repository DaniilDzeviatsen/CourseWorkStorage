package service;

import model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExchangeService {
    void putExchangeRate(CurrencyRate currencyRate);
    void removeExchangeRate(LocalDate requestedDate, String currencyCode);
    List<CurrencyRate> listExchangeRatesHistory (LocalDate requestedDate);

    BigDecimal  exchange (LocalDate requestedDate, BigDecimal sum,
                          CurrencyRate initialCurrencyCode, CurrencyRate aimCurrencyCode);
}
