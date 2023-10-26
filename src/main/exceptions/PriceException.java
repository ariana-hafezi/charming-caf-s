package exceptions;

// Exception for invalid menu item price.
public class PriceException extends MenuItemException {

    public PriceException(String msg) {
        super(msg);
    }
}
