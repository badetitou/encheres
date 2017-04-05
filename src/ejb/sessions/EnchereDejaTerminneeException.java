package ejb.sessions;

/**
 * Created by bverhaeg on 05/04/17.
 */
public class EnchereDejaTerminneeException extends Exception {
    @Override
    public String getMessage() {
        return "Les enchères sont déjà terminés";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
