package exceptions;

public class CurrencyParsingRateException extends ApplicationException {
    public String getDisplayMessage() {
        return "Ошибка  парсинга числа";
    }
}
