package ejb.sessions;

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
