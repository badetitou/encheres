package ejb.sessions;

/**
 * Created by badetitou on 19/03/17.
 */
public class EnchereNonDemarreeException extends Exception {
    @Override
    public String getMessage() {
        return "Les enchères n'ont pas encore démarré";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
