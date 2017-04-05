package ejb.sessions;

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
