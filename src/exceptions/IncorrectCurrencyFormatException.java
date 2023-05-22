package exceptions;

public class IncorrectCurrencyFormatException extends ApplicationException {
    public String getDisplayMessage() {
        return "Такой  валюты не существует";
    }
}
