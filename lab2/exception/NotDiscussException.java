package fudan.se.lab2.exception;

public class NotDiscussException extends RuntimeException {
    public NotDiscussException(){super("Has not discussed,so you can't change it");}
}
