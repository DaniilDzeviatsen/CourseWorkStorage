package service;

import exceptions.InvalidCommandException;
import exceptions.LocalCurrencyException;
import exceptions.NonExistingCurrencyException;
import model.CurrencyRate;
import repository.FileRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

public class ExchangeServiceImpl implements ExchangeService {
    private final FileRepository fileRepository;
    private final Currency localCurrency;


    public ExchangeServiceImpl(FileRepository fileRepository, Currency localCurrency) {
        this.localCurrency = localCurrency;
        this.fileRepository = Objects.requireNonNull(fileRepository);
    }


    public void removeExchangeRate(LocalDate requestedDate, String currencyCode) {
        if (!fileRepository.removeCurrencyRate(requestedDate, currencyCode)) {
            throw new InvalidCommandException();
        }
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
            if (rate.getCurrencyCode().toString().equals(initialCurrencyCode)) {
                initialCourse = rate.getBuyRate();

            } else if (rate.getCurrencyCode().toString().equals(aimCurrencyCode)) {
                aimCourse = rate.getSellRate();
            } else if (initialCurrencyCode.equals(localCurrency.toString())) {
                initialCourse = BigDecimal.ONE;
            } else if (aimCurrencyCode.equals(localCurrency.toString())) {
                aimCourse = BigDecimal.ONE;
            }
        }
        try {
            BigDecimal finalSum = initialCourse.multiply(sum);
            return finalSum.divide(aimCourse, 4, RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new NonExistingCurrencyException();
        }
    }

    public void putExchangeRate(LocalDate requestedDate, Currency currency, BigDecimal buyRate, BigDecimal
            sellRate) {

        CurrencyRate newCurrencyRate = new CurrencyRate(currency, sellRate, buyRate);

        if (currency == localCurrency) {
            throw new LocalCurrencyException();
        } else {
            fileRepository.putExchangeRates(newCurrencyRate, requestedDate);
        }
    }
}
