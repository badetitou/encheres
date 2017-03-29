package ejb.sessions;

/**
 * Created by badetitou on 23/03/17.
 */
public class PourcentageIncorrectException extends Exception {

    @Override
    public String getMessage() {
        return "Valeur pourcentage < 0 interdit";
    }
}
