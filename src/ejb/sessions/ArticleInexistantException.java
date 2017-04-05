package ejb.sessions;

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
