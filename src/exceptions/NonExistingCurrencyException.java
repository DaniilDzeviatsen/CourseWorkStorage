package exceptions;

public class NonExistingCurrencyException extends ApplicationException {
    @Override
    public String getDisplayMessage() {
        return "Данные о  курсе валюты отсутствуют";
    }
}
