package ejb.sessions;

/**
 * Created by badetitou on 19/03/17.
 */
public class ArticleDejaExistantException extends Exception {
    @Override
    public String getMessage() {
        return "Un article avec le même code existe déjà";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
