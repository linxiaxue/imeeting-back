package fudan.se.lab2.exception;

public class PaperHasBeenSubmittedException extends RuntimeException{
    public PaperHasBeenSubmittedException(){
        super("this paper has been submitted");
    }
}
