package ejb.sessions;

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
