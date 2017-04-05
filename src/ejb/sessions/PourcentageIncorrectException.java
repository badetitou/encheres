package ejb.sessions;

public class PourcentageIncorrectException extends Exception {

    @Override
    public String getMessage() {
        return "Valeur pourcentage < 0 interdit";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
