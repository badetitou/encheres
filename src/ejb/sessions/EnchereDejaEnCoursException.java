package ejb.sessions;

public class EnchereDejaEnCoursException extends Exception {

    @Override
    public String getMessage() {
        return "Les enchères sont déjà en cours";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
