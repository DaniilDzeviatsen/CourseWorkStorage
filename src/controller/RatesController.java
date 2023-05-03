package controller;

import exceptions.*;
import model.CurrencyRate;
import service.ExchangeServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
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
            if (!ifInputDateCorrect(options.get(0))) {
                throw new InputDateFormatException();
            }

            switch (command) {
                case "admin/putExchangeRate" -> handlePutExchangeRateCommand(options);
                case "client/listExchangeRates" -> handleListExchangeRatesCommand(options);
                case "admin/removeExchangeRate" -> handleRemoveExchangeRate(options);
                case "client/exchange" -> handleExchange(options);
                default -> throw new CommandNotFoundException();
            }
        } catch (ApplicationException e) {
            System.err.println(e.getDisplayMessage());

        } catch (Exception e) {
            System.err.println("Неизвестная ошибка");
        }

    }

    public void handleRemoveExchangeRate(List<String> options) {
        validateOptionsCount(options, 2);
        if (ifInputCurrencyCorrect(options.get(1))) {
            LocalDate requestedDate = LocalDate.parse(options.get(0));
            String currencyCode = options.get(1);
            if (service.removeExchangeRate(requestedDate, currencyCode)) {
                System.out.println("ExchangeRate deleted");
            } else System.err.println("Такой валюты нет в списке");
        }
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
            for (int i = 0; i < dayRates.size(); i++) {
                System.out.println(dayRates.get(i));
            }
        }
    }


    public void handlePutExchangeRateCommand(List<String> options) {
        validateOptionsCount(options, 4);
        if (ifInputCurrencyCorrect(options.get(1)) && (ifInputDataIsCurrency(options.get(2))
                && ifInputDataIsCurrency(options.get(3)) && ifInputCurseIsPositive(options.get(2)) &&
                ifInputCurseIsPositive(options.get(3)))) {
            LocalDate requestedDate = LocalDate.parse(options.get(0));
            Currency currency = Currency.getInstance(options.get(1));
            BigDecimal buyRate = BigDecimal.valueOf(Double.parseDouble(options.get(2)));
            BigDecimal sellRate = BigDecimal.valueOf(Double.parseDouble(options.get(3)));
            if (service.putExchangeRate(requestedDate, currency, buyRate, sellRate)) {
                System.out.println("ExchangeRate saved");
            }
        }
    }

    private void validateOptionsCount(List<String> options, int requiredCount) {
        if (options.size() != requiredCount) {
            throw new InvalidCommandException();
        }
    }

    public void handleExchange(List<String> options) {
        validateOptionsCount(options, 4);
        if (ifInputCurrencyCorrect(options.get(2)) && (ifInputCurrencyCorrect(options.get(3))
                && ifInputCurseIsPositive(options.get(1)))) {
            LocalDate requestedDate = LocalDate.parse(options.get(0));
            BigDecimal sum = BigDecimal.valueOf(Double.parseDouble(options.get(1)));
            String initialCurrencyCode = options.get(2);
            String aimCurrencyCode = options.get(3);
            System.out.println(service.exchange(requestedDate, sum, initialCurrencyCode, aimCurrencyCode));
        }
    }

    private boolean ifInputDateCorrect(String requestedDate) {
        try {
            LocalDate.parse(requestedDate);
            return true;
        } catch (Exception e) {
            throw new InputDateFormatException();
        }
    }

    private boolean ifInputCurrencyCorrect(String inputCurrency) {

        try {
            Currency.getInstance(inputCurrency);
            return true;
        } catch (Exception e) {
            throw new IncorrectCurrencyFormatException();
        }
    }

    private boolean ifInputDataIsCurrency(String inputCurrency) {
        try {
            BigDecimal bigDecimal = BigDecimal.valueOf(Double.parseDouble(inputCurrency));
            return true;
        } catch (NumberFormatException e) {
            throw new CurrencyParsingRateException();
        }
    }

    private boolean ifInputCurseIsPositive(String inputCurrency) {
        boolean ifPositive = false;
        BigDecimal bigDecimal = BigDecimal.valueOf(Double.parseDouble(inputCurrency));
        ifPositive = bigDecimal.compareTo(BigDecimal.valueOf(0)) == 1;
        if (ifPositive) {
            return true;
        } else
            throw new CurrencyPositiveCourseException();
    }
}

