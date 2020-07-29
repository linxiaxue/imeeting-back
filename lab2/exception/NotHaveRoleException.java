package fudan.se.lab2.exception;

public class NotHaveRoleException extends RuntimeException {
    public NotHaveRoleException(){
        super("you don't have this role");
    }
}
