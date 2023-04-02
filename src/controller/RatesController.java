package controller;

import exceptions.ApplicationException;
import exceptions.InvalidCommandException;
import model.CurrencyRate;
import service.ExchangeServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class RatesController {
    private final ExchangeServiceImpl service;

    public RatesController(ExchangeServiceImpl service) {
        Objects.requireNonNull(service);
        this.service = service;
    }

    public void handle(String command, List<String> options) {
        try {
            if (command.isEmpty()) {
                throw new InvalidCommandException();
            }
            switch (command) {
                case "admin/putExchangeRate" -> handlePutExchangeRateCommand(options);
                case "client/listExchangeRates" -> handleListExchangeRatesCommand(options);
                case "admin/removeExchangeRate" -> handleRemoveExchangeRate(options);
                case "client/exchange" -> handleExchange(options);
            }
        } catch (ApplicationException e) {
            System.err.println(e);

        } catch (Exception e) {
            System.err.println("Неизвестная ошибка");
        }

    }

    public void handleRemoveExchangeRate(List<String> options) {
        validateOptionsCount(options, 2);
        LocalDate requestedDate = LocalDate.parse(options.get(0));
        String currencyCode = options.get(1);
        service.removeExchangeRate(requestedDate, currencyCode);
        System.out.println("ExchangeRate deleted");
    }

    public void handleListExchangeRatesCommand(List<String> options) {
        validateOptionsCount(options, 1);
        LocalDate requestedDate = LocalDate.parse(options.get(0));
        List<CurrencyRate> dayRates = service.listExchangeRatesHistory(requestedDate);
        System.out.printf("%s %s %s", "Валюта", "Покупка", "Продажа");
        System.out.println();
        if (dayRates.isEmpty()) {
            System.out.println("Нет курсов");
        } else {
            System.out.printf("%5s %5s %5s", dayRates.get(0), dayRates.get(1), dayRates.get(2));
            System.out.println();
        }
    }



    public void handlePutExchangeRateCommand(List<String> options) {
        validateOptionsCount(options, 4);
        LocalDate requestedDate = LocalDate.parse(options.get(0));
        String currencyCode = options.get(1);
        BigDecimal buyRate = BigDecimal.valueOf(Double.parseDouble(options.get(2)));
        BigDecimal sellRate = BigDecimal.valueOf(Double.parseDouble(options.get(3)));
        service.putExchangeRate(requestedDate, currencyCode, buyRate, sellRate);
        System.out.println("ExchangeRate saved");
    }


    private void validateOptionsCount(List<String> options, int requiredCount) {
        if (options.size() != requiredCount) {
            throw new InvalidCommandException();
        }
    }

    public void handleExchange(List<String> options) {
        validateOptionsCount(options, 4);
        LocalDate requestedDate = LocalDate.parse(options.get(0));
        BigDecimal sum = BigDecimal.valueOf(Double.parseDouble(options.get(1)));
        String initialCurrencyCode = options.get(2);
        String aimCurrencyCode = options.get(3);
        System.out.println(service.exchange(requestedDate, sum, initialCurrencyCode, aimCurrencyCode));
    }
}
