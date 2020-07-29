package fudan.se.lab2.exception;

public class ContributionHasBeenMade extends RuntimeException {
    public ContributionHasBeenMade(){
        super("this contribution has been made");
    }
}
