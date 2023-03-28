package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrencyRate {
    private  String currencyName;
    private BigDecimal sellRate;
    private BigDecimal buyRate;
     private LocalDate requestedDate;

    public CurrencyRate (LocalDate requestedDate, String currencyName, BigDecimal sellRate, BigDecimal buyRate){
        this.buyRate=buyRate;
        this.sellRate=sellRate;
        this.currencyName=currencyName;
        this.requestedDate=requestedDate;
    }

    public String getCurrencyName() {
        return currencyName;
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
