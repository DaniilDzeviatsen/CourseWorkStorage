package exceptions;

public class ConfuseException extends ApplicationException {
    public String getDisplayMessage() {
        return "Параметры заданы в неверном порядке";
    }
}
