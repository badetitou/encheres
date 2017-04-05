package ejb.sessions;

/**
 * Created by badetitou on 19/03/17.
 */
public class PrixInitialException extends Exception {
    @Override
    public String getMessage() {
        return "Prix Initial < 0 interdit";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
