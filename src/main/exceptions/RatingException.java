package exceptions;

// Exception for invalid menu item rating.
public class RatingException extends RuntimeException {

    public RatingException(String msg) {
        super(msg);
    }
}
