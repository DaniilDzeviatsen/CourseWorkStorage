package exceptions;

public class InvalidCommandException extends ApplicationException {
    public String getDisplayMessage() {
        return "Неверный  формат команды";
    }
}
