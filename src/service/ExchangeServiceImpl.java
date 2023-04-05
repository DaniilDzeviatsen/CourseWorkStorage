package service;

import Repository.RatesFileRepository;
import exceptions.ApplicationException;
import exceptions.LocalCurrencyException;
import exceptions.NonExistingCurrencyException;
import model.CurrencyRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
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


    public boolean removeExchangeRate(LocalDate requestedDate, String currencyCode) {
        if (fileRepository.removeCurrencyRate(requestedDate, currencyCode)) {
            return true;
        }
        return false;
    }


    public List<CurrencyRate> listExchangeRatesHistory(LocalDate requestedDate) {
        List<CurrencyRate> rates = fileRepository.listCurrencyRates(requestedDate);
        return Collections.unmodifiableList(rates);
    }


    public BigDecimal exchange(LocalDate requestedDate, BigDecimal sum,
                               String initialCurrencyCode, String aimCurrencyCode) {
        List<CurrencyRate> listOfExistingRates = fileRepository.listCurrencyRates(requestedDate);
        BigDecimal aimCourse = null;
        BigDecimal initialCourse = null;

        for (CurrencyRate rate : listOfExistingRates) {
            if (rate.getCurrencyCode().equals(initialCurrencyCode)) {
                initialCourse = rate.getBuyRate();

            } else if (rate.getCurrencyCode().equals(aimCurrencyCode)) {
                aimCourse = rate.getSellRate();
            }
        }
        try {
            BigDecimal finalSum = initialCourse.multiply(sum);
            return finalSum.divide(aimCourse, 4, RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new NonExistingCurrencyException();
        }
    }

    public boolean putExchangeRate(LocalDate requestedDate, String currencyCode, BigDecimal buyRate, BigDecimal
            sellRate) {
        boolean ifAdded = false;
        CurrencyRate newCurrencyRate = new CurrencyRate(currencyCode, sellRate, buyRate);
        try {
            if (Currency.getInstance(currencyCode) == localCurrency) {
                throw new LocalCurrencyException();
            }
            fileRepository.putExchangeRates(newCurrencyRate, requestedDate);
            ifAdded = true;
        } catch (ApplicationException e) {
            System.err.println(e.getDisplayMessage());
        }
        return ifAdded;
    }
}
