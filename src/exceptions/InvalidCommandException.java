package exceptions;

public class InvalidCommandException extends ApplicationException {
    @Override
    public String getDisplayMessage() {
        return "Неверный  формат команды";
    }
}
