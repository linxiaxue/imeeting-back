package fudan.se.lab2.exception;

public class UserHasBeenInvitedException extends RuntimeException {
    public UserHasBeenInvitedException(){
        super("user has been invited");
    }
}
