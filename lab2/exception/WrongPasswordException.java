package fudan.se.lab2.exception;

public class WrongPasswordException extends RuntimeException {

    private static final long serialVersionUID = -6074753940710869977L;

    public WrongPasswordException() {
        super("wrong password");
    }
}
