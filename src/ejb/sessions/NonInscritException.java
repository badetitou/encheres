package ejb.sessions;

public class NonInscritException extends Exception {
    @Override
    public String getMessage() {
        return "Le client n'est pas inscrit";
    }

    @Override
    public void printStackTrace() {
        System.out.println(getMessage());
    }
}
