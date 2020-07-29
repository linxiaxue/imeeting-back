package fudan.se.lab2.exception;

public class CanNotEditException extends RuntimeException {
    public CanNotEditException(){
        super("Do not have enough members to review all papers");
    }
}
