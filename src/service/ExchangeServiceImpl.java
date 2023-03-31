package service;

import Repository.RatesFileRepository;
import model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ExchangeServiceImpl /*implements ExchangeService*/ {
    private final RatesFileRepository fileRepository;

    public ExchangeServiceImpl(RatesFileRepository fileRepository) {
        this.fileRepository = Objects.requireNonNull(fileRepository);
    }


    public void removeExchangeRate(LocalDate requestedDate, String currencyCode) {

    }


    public List<CurrencyRate> listExchangeRatesHistory(LocalDate requestedDate) {
        List<CurrencyRate> rates = fileRepository.listCurrencyRates(requestedDate);
        return Collections.unmodifiableList(rates);
    }


    public BigDecimal exchange(LocalDate requestedDate, BigDecimal sum,
                               CurrencyRate initialCurrencyCode, CurrencyRate aimCurrencyCode) {
        return null;
    }

    public void putExchangeRate(LocalDate requestedDate, String currencyCode, BigDecimal buyRate, BigDecimal sellRate) {
        CurrencyRate newCurrencyRate = new CurrencyRate(currencyCode, sellRate, buyRate);
        fileRepository.saveCurrencyRate(newCurrencyRate, requestedDate);
    }
}
