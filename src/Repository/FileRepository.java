package Repository;

import model.CurrencyRate;

import java.time.LocalDate;
import java.util.List;

public interface FileRepository {
    List<CurrencyRate> listCurrencyRates(LocalDate requestedDate);

    boolean saveCurrencyRate(CurrencyRate currencyRate, LocalDate requestedDate);

    void removeCurrencyRate(LocalDate requestedDate, String currencyCode);
}
