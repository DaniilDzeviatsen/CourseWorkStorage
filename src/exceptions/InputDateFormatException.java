package exceptions;

public class InputDateFormatException extends ApplicationException {
    public String getDisplayMessage() {
        return "Неверный формат даты";
    }
}
