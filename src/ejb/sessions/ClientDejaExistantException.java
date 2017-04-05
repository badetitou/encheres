package ejb.sessions;

/**
 * Created by badetitou on 19/03/17.
 */
public class ClientDejaExistantException extends Exception {
    @Override
    public String getMessage() {
        return "Client déjà existant";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
