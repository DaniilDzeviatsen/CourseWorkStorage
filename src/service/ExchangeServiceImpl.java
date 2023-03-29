package service;

import Repository.FileRepository;
import model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ExchangeServiceImpl implements ExchangeService{
    private final FileRepository fileRepository;
public ExchangeServiceImpl(FileRepository fileRepository){
    this.fileRepository= Objects.requireNonNull(fileRepository);
}

    @Override
    public void removeExchangeRate(LocalDate requestedDate, String currencyCode) {

    }

    @Override
    public List<CurrencyRate> listExchangeRatesHistory(LocalDate requestedDate) {
        return null;
    }

    @Override
    public BigDecimal exchange(LocalDate requestedDate, BigDecimal sum, CurrencyRate initialCurrencyCode, CurrencyRate aimCurrencyCode) {
        return null;
    }

    public void putExchangeRate(CurrencyRate currencyRate){

}
}
