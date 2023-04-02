package service;

import Repository.RatesFileRepository;
import exceptions.LocalCurrencyException;
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


    public void removeExchangeRate(LocalDate requestedDate, String currencyCode) {
        fileRepository.removeCurrencyRate(requestedDate, currencyCode);
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
        BigDecimal finalSum = initialCourse.multiply(sum);
        return finalSum.divide(aimCourse, 4, RoundingMode.HALF_UP);

    }

    public void putExchangeRate(LocalDate requestedDate, String currencyCode, BigDecimal buyRate, BigDecimal
            sellRate) {
        CurrencyRate newCurrencyRate = new CurrencyRate(currencyCode, sellRate, buyRate);
       // List<CurrencyRate> listOfExistingRates = fileRepository.listCurrencyRates(requestedDate);
        /*ListIterator<CurrencyRate> i=listOfExistingRates.listIterator();
        while(i.hasNext()){
            CurrencyRate currencyRate= i.next();
            if (currencyRate.getCurrencyCode().equals(currencyCode)){
                i.set(newCurrencyRate);
            }
        }*/

        if (Currency.getInstance(currencyCode) == localCurrency) {
            System.err.println("Невозможно добавить базовую валюту");
            throw new LocalCurrencyException();
        }

        fileRepository.putExchangeRates(newCurrencyRate, requestedDate);
    }
}
