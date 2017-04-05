package ejb.sessions;

/**
 * Created by badetitou on 19/03/17.
 */
public class EnchereCloseException extends Exception {
    @Override
    public String getMessage() {
        return "Les enchères sont closes";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
