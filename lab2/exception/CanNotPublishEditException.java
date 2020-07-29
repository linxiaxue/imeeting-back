package fudan.se.lab2.exception;

public class CanNotPublishEditException extends RuntimeException {
    public CanNotPublishEditException(){super("the papers have not been reviewed completely");}
}
