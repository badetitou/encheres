package ejb.sessions;

/**
 * Created by bverhaeg on 05/04/17.
 */
public class ClientInexistantException extends Exception {
    @Override
    public String getMessage() {
        return "Client inexistant";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
