package ejb.sessions;

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
