package ejb.sessions;

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
