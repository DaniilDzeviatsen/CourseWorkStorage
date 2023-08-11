package exceptions;

public class CommandNotFoundException extends ApplicationException {
    public String getDisplayMessage() {
        return "Неизвестная команда";
    }
}
