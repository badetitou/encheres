package ejb.sessions;

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
