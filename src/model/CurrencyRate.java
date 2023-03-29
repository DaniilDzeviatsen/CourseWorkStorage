package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class CurrencyRate {
    private String currencyCode;
    private Currency currency;
    private BigDecimal sellRate;
    private BigDecimal buyRate;
    private LocalDate requestedDate;

    public CurrencyRate(LocalDate requestedDate, String currencyCode, BigDecimal sellRate, BigDecimal buyRate) {
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.currency = Currency.getInstance(currencyCode);
        this.requestedDate = requestedDate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Currency getCurrency() {
        return currency;
    }

    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }
}
