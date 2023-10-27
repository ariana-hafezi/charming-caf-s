package exceptions;

// Exception for invalid menu item price.
public class PriceException extends RuntimeException {

    public PriceException(String msg) {
        super(msg);
    }
}
