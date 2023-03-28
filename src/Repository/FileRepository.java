package Repository;

import model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public interface FileRepository {
    List<CurrencyRate> listCurrencyRates(LocalDate requestedDate);
    void putCurrencyRate(CurrencyRate currencyRate);
    void removeCurrencyRate(LocalDate requestedDate, String currencyCode);
    BigDecimal exchange(LocalDate requestedDate, BigDecimal sum, String initialCurrencyCode, String aimCurrencyCode);


}
