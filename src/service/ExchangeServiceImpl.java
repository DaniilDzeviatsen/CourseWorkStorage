package service;

import Repository.RatesFileRepository;
import exceptions.LocalCurrencyException;
import model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

public class ExchangeServiceImpl /*implements ExchangeService*/ {
    private final RatesFileRepository fileRepository;
    private Currency localCurrency;


    public ExchangeServiceImpl(RatesFileRepository fileRepository, Currency localCurrency) {
        this.localCurrency = localCurrency;
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
        if (Currency.getInstance(currencyCode) == localCurrency) {
            System.err.println("Невозможно добавить базовую   валюту");
            throw new LocalCurrencyException();
        }

        fileRepository.saveCurrencyRate(newCurrencyRate, requestedDate);
    }
}
