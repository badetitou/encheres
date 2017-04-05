package ejb.sessions;

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
