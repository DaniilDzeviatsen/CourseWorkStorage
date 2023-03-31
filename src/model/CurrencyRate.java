package model;

import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyRate {
    private String currencyCode;
    private Currency currency;
    private BigDecimal sellRate;
    private BigDecimal buyRate;
    //private LocalDate requestedDate;

    public CurrencyRate(String currencyCode, BigDecimal sellRate, BigDecimal buyRate) {
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.currency = Currency.getInstance(currencyCode);
        //this.requestedDate = requestedDate;
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Currency getCurrency() {
        return currency;
    }

    /*public LocalDate getRequestedDate() {
        return requestedDate;
    }*/

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public String toString() {
        return String.format("%-5s%6s%8s\n", currencyCode, buyRate, sellRate);
    }
}
