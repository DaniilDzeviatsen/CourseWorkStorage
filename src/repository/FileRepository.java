package repository;

import model.CurrencyRate;

import java.time.LocalDate;
import java.util.List;

public interface FileRepository {
    List<CurrencyRate> listCurrencyRates(LocalDate requestedDate);

    void putExchangeRates(CurrencyRate currencyRate, LocalDate requestedDate);

    boolean removeCurrencyRate(LocalDate requestedDate, String currencyCode);
}
