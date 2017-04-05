package ejb.sessions;

/**
 * Created by badetitou on 19/03/17.
 */
public class InscriptionImpossibleException extends Exception {
    @Override
    public String getMessage() {
        return "L'inscription est impossible";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
