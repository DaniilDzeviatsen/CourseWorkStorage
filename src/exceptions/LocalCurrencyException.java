package exceptions;

public class LocalCurrencyException extends ApplicationException {

    @Override
    public String getDisplayMessage() {
        return "Базовая валюта не может быть добавлена";
    }
}
