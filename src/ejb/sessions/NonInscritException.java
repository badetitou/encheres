package ejb.sessions;

/**
 * Created by badetitou on 19/03/17.
 */
public class NonInscritException extends Exception {
    @Override
    public String getMessage() {
        return "Le client n'est pas inscrit";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
