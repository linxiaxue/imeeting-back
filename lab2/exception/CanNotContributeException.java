package fudan.se.lab2.exception;

public class CanNotContributeException extends RuntimeException {
    public CanNotContributeException(){
        super(" can't make contribution.");
    }
}
