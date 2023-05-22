package exceptions;

public abstract class ApplicationException extends RuntimeException {
    public abstract String getDisplayMessage();
}
