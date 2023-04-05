package exceptions;

public class CurrencyPositiveCourseException extends ApplicationException {
    public String getDisplayMessage() {
        return "Курс валюты должен быть положительным";
    }
}
