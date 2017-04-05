package ejb.sessions;

/**
 * Created by bverhaeg on 05/04/17.
 */
public class EnchereDejaEnCoursException extends Exception {

    @Override
    public String getMessage() {
        return "Les enchères sont déjà en cours";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
