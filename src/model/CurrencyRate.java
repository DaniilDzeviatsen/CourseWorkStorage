package model;

import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyRate {
    private final Currency currency;
    private final BigDecimal sellRate;
    private final BigDecimal buyRate;


    public CurrencyRate(Currency currency, BigDecimal sellRate, BigDecimal buyRate) {
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.currency = currency;
    }

    public Currency getCurrencyCode() {
        return currency;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public String toString() {
        return String.format("%-5s%6s%8s\n", currency, buyRate, sellRate);
    }
}
