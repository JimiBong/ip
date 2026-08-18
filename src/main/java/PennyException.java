public class PennyException extends Exception {

    public PennyException(String message) {
        super(message);
    }

    public PennyException(String message, Throwable cause) {
        super(message, cause);
    }
}