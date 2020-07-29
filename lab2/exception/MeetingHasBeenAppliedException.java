package fudan.se.lab2.exception;

public class MeetingHasBeenAppliedException extends RuntimeException{
    private static final long serialVersionUID = -6074753940710869977L;

    public MeetingHasBeenAppliedException() {
        super("this meeting has been applied,please change abbreviation");
    }
}
