package exceptions;

// Exception for invalid menu item rating.
public class RatingException extends MenuItemException {

    public RatingException(String msg) {
        super(msg);
    }
}
