package ejb.sessions;

/**
 * Created by badetitou on 19/03/17.
 */
public class ArticleInexistantException extends Exception {
    @Override
    public String getMessage() {
        return "Article Inexistant";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
